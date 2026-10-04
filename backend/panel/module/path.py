import os
import shutil


def join_paths(*args) -> str:
    return os.path.join(*args)


def dirname(path: str) -> str:
    return os.path.dirname(path)


def basename(path: str) -> str:
    return os.path.basename(path)


def normpath(path: str) -> str:
    return os.path.normpath(path)


def abspath(path: str) -> str:
    return os.path.abspath(path)


def makedirs(path: str):
    if not os.path.exists(path):
        os.makedirs(path)


def rmtree(path: str):
    if os.path.exists(path):
            shutil.rmtree(path)