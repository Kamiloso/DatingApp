import os
import subprocess


def shell_execute(command: str, silent=False) -> subprocess.CompletedProcess:
    return subprocess.run(command, shell=True, capture_output=silent, text=True)


def clear():
    shell_execute('cls' if os.name == 'nt' else 'clear')


def pause():
    input("Press Enter to continue...")