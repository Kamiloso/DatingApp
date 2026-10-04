from typing import Callable

from module.shell import clear


def preprint[T](function: Callable[[], T]) -> T:
    print()
    return function()


def preclear[T](function: Callable[[], T]) -> T:
    clear()
    return function()