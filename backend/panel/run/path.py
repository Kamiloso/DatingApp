from module.path import join_paths, dirname, abspath
from module.shell import getargv


PANEL_DIR = dirname(abspath(getargv(0)))

COMPOSE_DIR = join_paths(PANEL_DIR, "..")

COMPOSE_FILE = join_paths(COMPOSE_DIR, "docker-compose.yml")
COMPOSE_DEV_FILE = join_paths(COMPOSE_DIR, "docker-compose-dev.yml")
COMPOSE_IT_FILE = join_paths(COMPOSE_DIR, "docker-compose-it.yml")