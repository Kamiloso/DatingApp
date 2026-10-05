from run.docker import get_docker_state, get_compose_state
from module.shell import shell_execute, getargv
from module.path import normpath
from module.menu import Menu
from run.path import COMPOSE_FILE, COMPOSE_DEV_FILE, COMPOSE_IT_FILE


PROJECT_NAME = "dating-app"

USE_DEFAULT = f'-p {PROJECT_NAME} -f "{normpath(COMPOSE_FILE)}"'
USE_DEFAULT_DEV = f'{USE_DEFAULT} -f "{normpath(COMPOSE_DEV_FILE)}"'
USE_DEFAULT_IT = f'{USE_DEFAULT} -f "{normpath(COMPOSE_IT_FILE)}"'
USE_DEFAULT_DEV_IT = f'{USE_DEFAULT_DEV} -f "{normpath(COMPOSE_IT_FILE)}"'

UP_FLAGS = "--force-recreate --build"
DOWN_FLAGS = "--profile '*' down --remove-orphans"


def main():
    is_dev_only = getargv(1) == "--dev-only"

    options = [
        ("Run production", lambda: shell_execute(f'docker compose {USE_DEFAULT} up {UP_FLAGS}'), True),
        ("Run development", lambda: shell_execute(f'docker compose {USE_DEFAULT_DEV} up {UP_FLAGS}'), True),
        ("Run integration tests", lambda: shell_execute(f'docker compose {USE_DEFAULT_IT} up {UP_FLAGS}'), True),
        ("Stop container", lambda: shell_execute(f'docker compose {USE_DEFAULT_DEV_IT} {DOWN_FLAGS}'), True),
        ("Nuke container", _ask_then_nuke_container, True),
    ]

    if is_dev_only:
        options = [options[1], options[3]]

    options += [
        ("Open MySQL console", lambda: shell_execute("docker exec -it dating-app-db mysql -u root dating_app"), True),
        ("Open Redis console", lambda: shell_execute("docker exec -it dating-app-redis redis-cli"), True),
        ("View logs", lambda: shell_execute(f'docker compose {USE_DEFAULT_DEV_IT} --profile "*" logs -f'), True),
    ]

    Menu(
        title="BACKEND DEV RUN MANAGER",
        options=options,
        lines_provider=lambda: [
            f"Docker state: {get_docker_state()}",
            f"Compose state: {get_compose_state(PROJECT_NAME, COMPOSE_FILE, COMPOSE_DEV_FILE, COMPOSE_IT_FILE)}",
        ],
    ).run()


def _ask_then_nuke_container():
    print("\nAre you sure you want to nuke the container? This will remove all volumes and cannot be undone.")
    print("Type 'CONFIRM' to proceed, or anything else to cancel.")

    choice = input("> ")
    if choice == "CONFIRM":
        shell_execute(f'docker compose {USE_DEFAULT_DEV_IT} {DOWN_FLAGS} -v')
        print("\nThe container is gone along with the whole database. What have you done!?")
        return

    print("\nNuke cancelled. You saved the world!")


if __name__ == "__main__":
    main()