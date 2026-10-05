from module.menu import Menu
from building.env_builder import build_environment
from building.dev_starter import run_temp_environment
from building.dependency_check import check_dependencies


def main():
    Menu(
        title="BACKEND BUILD MANAGER",
        options=[
            ("Build environment", build_environment, True),
            ("Run environment (dev-only)", run_temp_environment, True),
            ("Check dependencies", check_dependencies, True),
        ],
        lines_provider=lambda: []
    ).run()


if __name__ == "__main__":
    main()