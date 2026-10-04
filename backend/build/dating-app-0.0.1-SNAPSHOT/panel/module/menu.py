from typing import Callable, Any

from module.shell import pause
from module.preaction import preclear, preprint


class Menu:

    def __init__(
        self, 
        title: str, 
        options: list[tuple[str, Callable[[], Any], bool]], 
        lines_provider: Callable[[], list[str]] | None = None
    ):
        self.title = title
        self.options = options
        self.lines_provider = lines_provider


    def run(self) -> None:
        while True:
            refresh_idx = preclear(self._display_options)
            selected = self._handle_input(refresh_idx)

            label, action, should_pause = selected

            if label == "Refresh":
                continue
            if label == "Exit":
                break

            action()

            if should_pause:
                preprint(pause)


    def _display_options(self) -> int:
        print(f"\n ----- {self.title} -----")
        
        if self.lines_provider:
            lines = self.lines_provider()
            if lines:
                print()
                for line in lines:
                    print(" " + line)
        
        print()
        for i, (label, _, _) in enumerate(self.options, start=1):
            print(f" {i} - {label}")

        refresh_idx = len(self.options) + 1
        
        print(f" {refresh_idx} - Refresh")
        print(" 0 - Exit")
        
        return refresh_idx


    def _handle_input(self, refresh_idx: int) -> tuple[str, Callable[[], Any], bool]:
        print("\n Select an option:")
        try:
            choice = int(input(" > ").strip())
            
            if choice == 0:
                return "Exit", lambda: None, False
            if choice == refresh_idx:
                return "Refresh", lambda: None, False

            if choice < 1 or choice > len(self.options):
                raise ValueError

            return self.options[choice - 1]

        except ValueError:
            print("\nInvalid input. Please enter a valid option number.")
            preprint(pause)
            return "Refresh", lambda: None, False