import configparser
import os

from module.path import join_paths, basename, dirname, normpath, rmtree, makedirs
from module.filesystem import perform_copy


EXPANSION_FILE_NAME = "file-imports.ini"

SOURCE_KEY = "Source"
TARGET_KEY = "Target"


def expand_template_dir(source_dir: str, target_dir: str):
    rmtree(target_dir)
    makedirs(target_dir)

    for item in os.listdir(source_dir):
        source_item = join_paths(source_dir, item)
        target_item = join_paths(target_dir, item)

        if os.path.isdir(source_item):
            expand_template_dir(source_item, target_item)
        else:
            filename = basename(source_item)
            if filename == EXPANSION_FILE_NAME:
                _expand_file(source_item, target_item)
            else:
                _perform_copy_warn(source_item, target_item)


def _expand_file(source_file: str, target_file: str):
    current_dir = dirname(source_file)
    alternate_dir = dirname(target_file)

    config = configparser.ConfigParser()
    config.read(source_file)

    for section in config.sections():
        source = join_paths(current_dir, config[section][SOURCE_KEY])
        target = join_paths(alternate_dir, config[section][TARGET_KEY])

        _perform_copy_warn(source, target)


def _perform_copy_warn(source: str, target: str):
    success = perform_copy(source, target)
    if not success:
        print(f"WARNING: No files were copied from '{normpath(source)}' to '{normpath(target)}'.")