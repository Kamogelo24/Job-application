## Purpose

This repository is a small, single-process Java console application: "Job Application Tracker".
The entrypoint is `Main.java` which prints a header and invokes `new Menu().start()` to launch the interactive CLI.

## Big-picture architecture

- Entrypoint: `Main.java` — calls `Menu.start()` to drive the UI.
- UI layer: `Menu.java` — console interaction (System.out/System.in). Keep UI logic here.
- Business logic: `ApplicationManager.java` — intended location for CRUD and workflows around applications.
- Persistence: `FileHandler.java` — place file I/O (load/save) here (project contains an empty `main.txt` which appears to be the intended storage file).
- Domain models: `Applicant.java`, `Application.java`, `JobApplication.java`, `Company.java`, `Status.java`, `User.java` — currently skeletons; they are single-class-per-file with no package declaration (default package).

Typical flow: `Main` -> `Menu` -> `ApplicationManager` -> `FileHandler` (read/write) working against model objects (e.g., `JobApplication`).

## Project-specific conventions and notes

- Default package: none of the Java files declare a `package` — keep new classes in the default package or add packages across all files simultaneously.
- Single-class-per-file with PascalCase filenames matching class names (Java convention is followed).
- Console-first UX: UI uses `System.out.println` and simple console prompts — follow this style for new features unless migrating to another UI.
- Persistence hint: `main.txt` exists (empty) — search/replace or implement a simple line-based CSV/JSON writer inside `FileHandler` if you need persistence.

## Build & run (Windows PowerShell)

This project has no build config (no Maven/Gradle). Use the JDK directly.

Compile all sources:

```powershell
javac *.java
```

Run the app:

```powershell
java Main
```

If you add packages, compile to an output directory and run with classpath:

```powershell
javac -d out src\**\*.java
java -cp out your.package.Main
```

## Integration points for automation or AI edits

- Menu.start() — update this method to add new prompts or call new ApplicationManager methods.
- ApplicationManager — centralize business rules here (search, add, update, status transitions).
- FileHandler — implement `load()` and `save()` methods; other modules should call FileHandler rather than doing ad-hoc I/O.

## What Copilot / AI agents should do first

1. Read `Main.java` and `Menu.java` to find the user flow. The main loop belongs in `Menu.start()`.
2. Implement/extend `ApplicationManager` for new commands (create/list/update/delete job applications).
3. Implement `FileHandler` with a small, stable text format (CSV or JSON) and add `load()`/`save()` methods called by `ApplicationManager` at startup/shutdown.
4. Keep changes in the default package unless you refactor all source files and update run/compile commands.

## Examples from this codebase

- Entrypoint: `Main.java` — calls `Menu menu = new Menu(); menu.start();` (modify `Menu.start()` to change the UX)
- Storage file: `main.txt` — an existing but empty file; prefer updating `FileHandler` to use that as data file.

## Limitations & discovered facts

- No tests, no build automation, no external dependencies. Assume a plain JDK environment.
- Many files are placeholders; take care to preserve filenames and class names when adding members.

If anything here is inaccurate or you want a different style (e.g., migrate to packages or add Maven), tell me which direction to take and I will update this instruction file.
