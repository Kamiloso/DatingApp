from module.path import join_paths
from module.shell import cmd_execute
from .template_expansion import expand_template_dir
from .path import DOCKER_TEMPLATE_DIR, BUILD_DIR


def run_temp_environment():
    print("\nBuilding temporary Docker environment...")

    source_dir = DOCKER_TEMPLATE_DIR
    target_dir = join_paths(BUILD_DIR, f".dev-build")

    run_file = join_paths(target_dir, "panel", "main_run.py")

    expand_template_dir(source_dir, target_dir, profile="dev-only")
    cmd_execute(f'python "{run_file}" --dev-only')