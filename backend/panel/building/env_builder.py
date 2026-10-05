import glob

from module.open import open_explorer_silently
from module.shell import cmd_execute
from module.path import join_paths, basename, normpath
from module.filesystem import rmtree
from .template_expansion import expand_template_dir
from .path import PROJECT_DIR, GRADLEW_FILE, JAR_SOURCE_DIR, JAR_SOURCE_PATTERN, DOCKER_TEMPLATE_DIR, BUILD_DIR


FALLBACK_VERSION = "0.0.0-UNKNOWN"


def build_environment():
    print("\nBuilding Gradle project...")
    rmtree(JAR_SOURCE_DIR)
    cmd_execute(f'"{GRADLEW_FILE}" clean bootJar', cwd=PROJECT_DIR)

    version = _get_version()

    print("\nDetected version:", version)
    print("\nBuilding Docker environment...")

    source_dir = DOCKER_TEMPLATE_DIR
    target_dir = join_paths(BUILD_DIR, f"dating-app-{version}")

    expand_template_dir(source_dir, target_dir)

    print(f"\nDone! Build is available at:\n{normpath(target_dir)}")
    open_explorer_silently(target_dir)


def _get_version() -> str:
    all_files = glob.glob(JAR_SOURCE_PATTERN)
    for file in all_files:
        filename = basename(file)
        if filename.startswith("dating-app-") and filename.endswith(".jar"):
            version = filename[len("dating-app-"):-len(".jar")]
            return version
    return FALLBACK_VERSION