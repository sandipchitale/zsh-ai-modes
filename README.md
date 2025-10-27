# zsh-ai-modes

A simple integration of `zsh` with locally running `gemma3:4b` model, running inside `Ollama`.

# Feature

Using <kbd>CTRL+n</kbd> you can cycle between three modes:

- normal shell mode (zsh starts in this mode)
- llm mode - ask any question in plain language
- command mode - describe the command to do something and let AI model generate the command to do it

No matter which mode you are in you can use the following keybindings:

- <kbd>CTRL+x CTRL+z</kbd> - normal mode
- <kbd>CTRL+x CTRL+l</kbd> - llm mode
- <kbd>CTRL+x +CTRL+g</kbd> - command generation mode

# How to use ?

- [Download](https://ollama.com/download) and start `ollama` service
- Pull and run `gemma3:4b` model

```zsh
ollama pull gemma3:4b
```

- Clone [this](https://github.com/sandipchitale/zsh-ai-modes) repo to `~/.zsh/zsh-ai-modes`

```zsh
mkdir -p ~/.zsh/ 
cd ~/.zsh/
git clone https://github.com/sandipchitale/zsh-ai-modes.git
```
- Build and copy jar

```zsh
cd ~/.zsh/zsh-ai-modes/aicli
chmod u+x ./gradlew
./gradlew assemble
cp build/libs/aicli.jar ..
```

- Add the following to your `~/.zshrc`

```zsh
source ~/.zsh/zsh-ai-modes/zsh-ai-modes
```

# Sample use

- Try different modes using <kbd>CTRL+n</kbd>

```text
/tmp/test
> ls -al
total 0
drwxr-xr-x.  2 sandipchitale sandipchitale   80 Oct 26 21:51 .
drwxrwxrwt. 86 root          root          2100 Oct 26 21:51 ..
-rw-r--r--.  1 sandipchitale sandipchitale    0 Oct 26 21:51 a
-rw-r--r--.  1 sandipchitale sandipchitale    0 Oct 26 21:51 b

/tmp/test
> # type CTRL+n to switch to llm mode

/tmp/test
> I am sandip                                                      LLM [ 🤖: gemma3:4b ]
                                                                   LLM [ 🤖: gemma3:4b 
  🤖  Working (llm mode) ...
Hello Sandip, it’s nice to meet you! Is there anything I can help you with today?

/tmp/test
> # type CTRL+n again to switch to command generation mode

/tmp/test
> list files in /tmp/test folder including hidden files        Command [ 🤖: gemma3:4b ]
                                                               Command [ 🤖: gemma3:4b ]
  🤖  Working (command mode) ...
💡 ls -la /tmp/test
▶️ y/N/e(dit) ? y

ls -la /tmp/test
total 0
drwxr-xr-x.  2 sandipchitale sandipchitale   80 Oct 26 21:51 .
drwxrwxrwt. 88 root          root          2160 Oct 26 21:52 ..
-rw-r--r--.  1 sandipchitale sandipchitale    0 Oct 26 21:51 a
-rw-r--r--.  1 sandipchitale sandipchitale    0 Oct 26 21:51 b

/tmp/test
> # type CTRL+n again to revert back to normal shell

/tmp/test
> 
```
