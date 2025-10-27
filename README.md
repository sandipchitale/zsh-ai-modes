# zsh-ai-modes

A simple integration of `zsh` with locally running model `gemma3:4b`, running inside `Ollama`.

# How to use ?

- Download and run `ollama` service
- Pull and run `gemma3:4b` model
- Clone this repo to `~/.zsh/zsh-ai-modes`

```zsh
mkdir -p ~/.zsh/ 
cd ~/.zsh/
git clone https://github.com/sandipchitale/zsh-ai-modes
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

- Try different modes using <kbd>CTRL+N</kbd>