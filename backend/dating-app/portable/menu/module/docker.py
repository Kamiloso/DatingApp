import subprocess


def get_docker_state() -> str:
    try:
        result = subprocess.run(
            ["docker", "info"],
            capture_output=True,
            text=True
        )
        return "running" if result.returncode == 0 else "not running"
    except FileNotFoundError:
        return "docker not installed"


import subprocess

def get_compose_state(compose_file_path: str) -> str:
    try:
        all_containers = subprocess.run(
            ["docker", "compose", "-f", compose_file_path, "ps", "-q", "-a"],
            capture_output=True,
            text=True
        )
        
        running_containers = subprocess.run(
            ["docker", "compose", "-f", compose_file_path, "ps", "-q", "--status", "running"],
            capture_output=True,
            text=True
        )

        if all_containers.returncode != 0:
            return "error"

        all_count = len([line for line in all_containers.stdout.splitlines() if line.strip()])
        running_count = len([line for line in running_containers.stdout.splitlines() if line.strip()])

        if all_count == 0:
            return "not created"
        if running_count == all_count:
            return "running"
        if running_count == 0:
            return "stopped"
        
        return f"partially running ({running_count}/{all_count})"
    except FileNotFoundError:
        return "docker not installed"