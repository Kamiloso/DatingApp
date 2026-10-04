import os
import subprocess
import sys
import webbrowser

from module.path import abspath


def open_explorer_silently(file_path: str):
    try:
        path = abspath(file_path)
        if sys.platform == "win32":
            os.startfile(path)
        elif sys.platform == "darwin":
            subprocess.Popen(["open", path], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        else:
            subprocess.Popen(["xdg-open", path], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
    except Exception:
        pass


def open_html_silently(file_path: str):
    try:
        webbrowser.open(f"file://{abspath(file_path)}")
    except Exception:
        pass