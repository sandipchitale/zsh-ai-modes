# zsh-ai-modes

A simple integration of `zsh` with locally running `gemma3:4b` model, running inside `Ollama`.

# How to use ?

- Download and run `ollama` service
- Pull and run `gemma3:4b` model
- Clone this repo to `~/.zsh/zsh-ai-modes`

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

- Try different modes using <kbd>CTRL+N</kbd>

```text
/tmp/test
> ls -al
total 0
drwxr-xr-x.  2 sandipchitale sandipchitale   80 Oct 26 21:51 .
drwxrwxrwt. 86 root          root          2100 Oct 26 21:51 ..
-rw-r--r--.  1 sandipchitale sandipchitale    0 Oct 26 21:51 a
-rw-r--r--.  1 sandipchitale sandipchitale    0 Oct 26 21:51 b
/tmp/test
> # type CTRL+N to switch to llm mode
/tmp/test
> I am sandip                                                  LLM [ 🤖: gemma3:4b ]
147756-llm: Hello Sandip, it’s nice to meet you! Is there anything I can help you with today?
> # type CTRL+N again to switch to command generation mode
/tmp/test
> list files in /tmp/test folder including hidden files        Command [ 🤖: gemma3:4b ]
💡 ls -la /tmp/test
▶️ y/N/e(dit) ? 

ls -la /tmp/test
total 0
drwxr-xr-x.  2 sandipchitale sandipchitale   80 Oct 26 21:51 .
drwxrwxrwt. 88 root          root          2160 Oct 26 21:52 ..
-rw-r--r--.  1 sandipchitale sandipchitale    0 Oct 26 21:51 a
-rw-r--r--.  1 sandipchitale sandipchitale    0 Oct 26 21:51 b
> # type CTRL+N again to revert back to normal shell
> 
```
