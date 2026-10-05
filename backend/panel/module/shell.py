import os
import subprocess
import sys


def shell_execute(command: str, silent=False, cwd=None) -> subprocess.CompletedProcess:
    return subprocess.run(command, shell=True, capture_output=silent, text=True, cwd=cwd)


def clear():
    shell_execute('cls' if os.name == 'nt' else 'clear')


def pause():
    input("Press Enter to continue...")


def getargv(n: int) -> str:
    return sys.argv[n] if len(sys.argv) < n else ""