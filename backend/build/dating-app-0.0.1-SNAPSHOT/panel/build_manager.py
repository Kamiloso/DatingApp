from module.menu import Menu
from building.env_builder import build_environment
from building.dependency_check import check_dependencies


def main():
    Menu(
        title="BACKEND BUILD MANAGER",
        options=[
            ("Build environment", build_environment, True),
            ("Check dependencies", check_dependencies, True),
        ],
        lines_provider=lambda: [
            "Welcome to the backend build manager!",
            "It makes building your project much easier.",
        ]
    ).run()


if __name__ == "__main__":
    main()