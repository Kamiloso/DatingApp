import os
import subprocess
import sys

from typing import Callable


def cmd_execute(command: str, silent=False, cwd=None) -> int:
    result = None
    try:
        result = subprocess.run(command, shell=True, capture_output=silent, text=True, cwd=cwd)
    except KeyboardInterrupt:
        print("\nProcess interrupted by user (SIGINT).")
    except Exception as e:
        print(f"\nException: {e}")

    if result is None:
        return -1
    
    return result.returncode


def shell_execute(command: str, silent=False, cwd=None) -> int:
    code = cmd_execute(command, silent=silent, cwd=cwd)
    if code != -1:
        print(f"\nProcess exited with code {code}")
    
    return code


def clear():
    cmd_execute('cls' if os.name == 'nt' else 'clear')


def pause():
    input("Press Enter to continue...")


def getargv(n: int) -> str:
    return sys.argv[n] if n < len(sys.argv) else ""