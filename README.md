# kcwe

EOL change utility that can navigate a nested directory structure.

>[!NOTE]
> Use `--recurse` argument when cloning: `git clone --recurse <URL>`

## Building

Use the gradle `fatJar` task to build and copy the `kcwe.jar` file
(located at `app/build/libs/`) to the appropriate destination.

## Recommended Usage

The recommended usage is to alias kcwe using bash or Powershell.

```ps1
function kcwe($ending, $path, $ignore) { &java -jar <PATH-TO-JAR> $ending $path $ignore }
```

```sh
kcwe() { java -jar <PATH-TO-JAR> "$@" ;}
```

## Arguments

```ps1
- `ending` EOL style: unix(\n), dos(\r\n), mac(\r)
- `path`   The file or directory to change the files of
- `ignore` Comma separated list of files or directories to ignore
```

## Flags

```ps1
- `h` Display help menu
- `n` Disable color output
```
