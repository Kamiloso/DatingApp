from typing import Callable

from run.docker import get_docker_state, get_compose_state
from module.preaction import preprint
from module.shell import shell_execute
from module.path import normpath
from module.menu import Menu
from project.path import COMPOSE_FILE, COMPOSE_DEV_FILE, COMPOSE_IT_FILE


PROJECT_NAME = "dating-app"
PROJECT_FLAG = f'-p {PROJECT_NAME}'

USE_DEFAULT = f'{PROJECT_FLAG} -f "{normpath(COMPOSE_FILE)}"'
USE_DEFAULT_DEV = USE_DEFAULT + f' -f "{normpath(COMPOSE_DEV_FILE)}"'
USE_DEFAULT_IT = USE_DEFAULT + f' -f "{normpath(COMPOSE_IT_FILE)}"'
USE_DEFAULT_DEV_IT = USE_DEFAULT_DEV + f' -f "{normpath(COMPOSE_IT_FILE)}"'


def main():
    Menu(
        title="BACKEND RUN MANAGER",
        options=[
            ("Run production", shell_lambda(f'docker compose {USE_DEFAULT} up --force-recreate --build'), True),
            ("Run development", shell_lambda(f'docker compose {USE_DEFAULT_DEV} up --force-recreate --build'), True),
            ("Run integration tests", shell_lambda(f'docker compose {USE_DEFAULT_IT} up --force-recreate --build'), True),
            ("Stop container", shell_lambda(f'docker compose {USE_DEFAULT_DEV_IT} --profile "*" down --remove-orphans'), True),
            ("Nuke container", shell_lambda(f'docker compose {USE_DEFAULT_DEV_IT} --profile "*" down -v --remove-orphans'), True),
            ("Open MySQL console", shell_lambda("docker exec -it dating-app-db mysql -u root dating_app"), True),
            ("Open Redis console", shell_lambda("docker exec -it dating-app-redis redis-cli"), True),
            ("View logs", shell_lambda(f'docker compose {USE_DEFAULT_DEV_IT} --profile "*" logs -f'), True),
        ],
        lines_provider=lambda: [
            f"Docker state: {get_docker_state()}",
            f"Compose state: {get_compose_state(PROJECT_NAME, COMPOSE_FILE, COMPOSE_DEV_FILE, COMPOSE_IT_FILE)}",
        ]
    ).run()


def shell_lambda(command: str) -> Callable[[], None]:
    def action() -> None:
        result = None
        try:
            result = preprint(lambda: shell_execute(command)).returncode
        except KeyboardInterrupt:
            preprint(lambda: print("Process interrupted by user (SIGINT)."))
        except Exception as e:
            preprint(lambda: print(f"Exception: {e}"))

        if result is not None:
            preprint(lambda: print(f"Command exited with code {result}"))
            
    return action


if __name__ == "__main__":
    main()