import os

from module.docker import get_docker_state, get_compose_state
from module.preaction import preprint, preclear
from module.shell import shell_execute, pause


SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
DOCKER_COMPOSE_FILE = os.path.normpath(os.path.join(SCRIPT_DIR, "../docker-compose.yml"))
DOCKER_COMPOSE_DEV_FILE = os.path.normpath(os.path.join(SCRIPT_DIR, "../docker-compose-dev.yml"))


def menu_ask() -> str:
    docker_state = get_docker_state()
    compose_state = get_compose_state(DOCKER_COMPOSE_FILE)

    print()
    print(" ----- MAIN MENU -----")
    print()
    print(f" Docker state: {docker_state}")
    print(f" Compose state: {compose_state}")
    print()
    print(" 1 - Run container")
    print(" 2 - Run container (dev)")
    print(" 3 - Stop container")
    print(" 4 - Open MySQL console")
    print(" 5 - Nuke container")
    print(" 6 - View logs")
    print(" 7 - Refresh")
    print(" 8 - Exit")
    print()
    print(" Select an option:")
    print(" > ", end="")

    try:
        option = int(input().strip())
    except ValueError:
        option = 0

    mappings = {
        1: "run_container",
        2: "run_container_dev",
        3: "stop_container",
        4: "open_mysql_console",
        5: "nuke_container",
        6: "view_logs",
        7: "refresh",
        8: "exit"
    }

    return mappings.get(option, "invalid_option")


def main():
    while True:
        choice = preclear(menu_ask)
        result = None

        try:
            if choice == "invalid_option":
                preprint(lambda: print("Invalid option. Please try again."))

            elif choice == "run_container":
                result = preprint(lambda: shell_execute(f'docker compose -f "{DOCKER_COMPOSE_FILE}" up')).returncode

            elif choice == "run_container_dev":
                result = preprint(lambda: shell_execute(f'docker compose -f "{DOCKER_COMPOSE_FILE}" -f "{DOCKER_COMPOSE_DEV_FILE}" up')).returncode

            elif choice == "stop_container":
                result = preprint(lambda: shell_execute(f'docker compose -f "{DOCKER_COMPOSE_FILE}" down')).returncode

            elif choice == "open_mysql_console":
                result = preprint(lambda: shell_execute("docker exec -it dating-app-db mysql -u root dating_app")).returncode

            elif choice == "nuke_container":
                result = preprint(lambda: shell_execute(f'docker compose -f "{DOCKER_COMPOSE_FILE}" down -v')).returncode

            elif choice == "view_logs":
                result = preprint(lambda: shell_execute(f'docker compose -f "{DOCKER_COMPOSE_FILE}" logs -f')).returncode

            else:
                if choice == "refresh": continue
                if choice == "exit": break
                exit(1)

        except KeyboardInterrupt:
            preprint(lambda: print("Process interrupted by user (SIGINT)."))
        except Exception as e:
            preprint(lambda: print(f"Exception: {e}"))

        if result is not None:
            preprint(lambda: print(f"Command exited with code {result}"))

        preprint(pause)


if __name__ == "__main__":
    main()