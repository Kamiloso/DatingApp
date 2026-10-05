import glob
import os
import shutil


def copy_file(source_file: str, target_file: str):
    shutil.copy2(source_file, target_file)


def copy_tree(source_dir: str, target_dir: str):
    shutil.copytree(source_dir, target_dir, dirs_exist_ok=True)


def makedirs(path: str):
    if not os.path.exists(path):
        os.makedirs(path)


def rmtree(path: str):
    if os.path.exists(path):
            shutil.rmtree(path)


def perform_copy(source: str, target: str) -> bool:
    success = False
    is_dir = source.endswith('/')
    
    for match in glob.glob(source):
        if is_dir and os.path.isdir(match):
            copy_tree(match, target)
            success = True
        elif not is_dir and os.path.isfile(match):
            copy_file(match, target)
            success = True

    return success