import os

from module.path import normpath
from module.shell import cmd_execute
from module.open import open_html_silently
from .path import PROJECT_DIR, GRADLEW_FILE, DEPENDENCY_CHECK_FILE


API_KEY_VARIABLE = "NVD_API_KEY"


def check_dependencies():
    print("\nChecking dependencies...")

    if API_KEY_VARIABLE in os.environ:
        cmd_execute(f'"{GRADLEW_FILE}" dependencyCheckAggregate', cwd=PROJECT_DIR)
        print(f"\nDependency check report generated at:\n{normpath(DEPENDENCY_CHECK_FILE)}")
        open_html_silently(DEPENDENCY_CHECK_FILE)
    else:
        print("\nERROR: NVD_API_KEY environment variable is missing.")
        print("1. Get your API key at: https://nvd.nist.gov/developers/request-an-api-key")
        print("2. Set it as an environment variable named NVD_API_KEY and restart this terminal.")
        print("3. Run this dependency check again.")