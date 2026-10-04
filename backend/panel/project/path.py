import os, sys

from module.path import join_paths, dirname


PANEL_DIR = dirname(os.path.abspath(sys.argv[0]))


# --- FOR BUILDING ---

ROOT_DIR = join_paths(PANEL_DIR, "..")
PROJECT_DIR = join_paths(ROOT_DIR, "dating-app")

GRADLEW_FILE = join_paths(PROJECT_DIR, f"gradlew{".bat" if os.name == "nt" else ""}")
DEPENDENCY_CHECK_FILE = join_paths(PROJECT_DIR, "build/reports/dependency-check/dependency-check-report.html")

JAR_SOURCE_DIR = join_paths(PROJECT_DIR, "build/libs")
JAR_SOURCE_PATTERN = join_paths(JAR_SOURCE_DIR, "dating-app-*.jar")

CONFIG_SOURCE_DIR = join_paths(PROJECT_DIR, "source/infrastructure/config")
CONFIG_SOURCE_PATTERN = join_paths(CONFIG_SOURCE_DIR, "application*.yml")

DOCKER_TEMPLATE_DIR = join_paths(ROOT_DIR, "docker-template")
BUILD_DIR = join_paths(ROOT_DIR, "build")


# --- FOR RUNNING ---

COMPOSE_DIR = join_paths(PANEL_DIR, "..")

COMPOSE_FILE = join_paths(COMPOSE_DIR, "docker-compose.yml")
COMPOSE_DEV_FILE = join_paths(COMPOSE_DIR, "docker-compose-dev.yml")
COMPOSE_IT_FILE = join_paths(COMPOSE_DIR, "docker-compose-it.yml")