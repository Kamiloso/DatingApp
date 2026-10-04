import glob
from typing import Callable

from module.open import open_explorer_silently
from module.shell import shell_execute
from module.path import join_paths, basename, normpath, rmtree
from project.path import PROJECT_DIR, GRADLEW_FILE, JAR_SOURCE_DIR, JAR_SOURCE_PATTERN, DOCKER_TEMPLATE_DIR, BUILD_DIR
from .template_expansion import expand_template_dir


FALLBACK_VERSION = "0.0.0-UNKNOWN"


def build_environment():
    print("\nBuilding Gradle project...")
    rmtree(JAR_SOURCE_DIR)
    shell_execute(f'"{GRADLEW_FILE}" clean bootJar', cwd=PROJECT_DIR)

    version = _get_version()

    print("\nDetected version:", version)
    print("\nBuilding Docker environment...")

    source_path = DOCKER_TEMPLATE_DIR
    target_path = join_paths(BUILD_DIR, f"dating-app-{version}")

    expand_template_dir(source_path, target_path)

    print(f"\nDone! Build is available at:\n{normpath(target_path)}")
    open_explorer_silently(target_path)


def _get_version() -> str:
    all_files = glob.glob(JAR_SOURCE_PATTERN)
    for file in all_files:
        filename = basename(file)
        if filename.startswith("dating-app-") and filename.endswith(".jar"):
            version = filename[len("dating-app-"):-len(".jar")]
            return version
    return FALLBACK_VERSION