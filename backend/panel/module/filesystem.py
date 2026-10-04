import glob
import os
import shutil


def perform_copy(source: str, target: str) -> bool:
    success = False
    is_dir = source.endswith('/')
    
    for match in glob.glob(source):
        if is_dir and os.path.isdir(match):
            shutil.copytree(match, target, dirs_exist_ok=True)
            success = True
        elif not is_dir and os.path.isfile(match):
            shutil.copy2(match, target)
            success = True

    return success