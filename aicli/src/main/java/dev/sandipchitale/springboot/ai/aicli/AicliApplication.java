package dev.sandipchitale.springboot.ai.aicli;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class AicliApplication {

    public static void main(String[] args) {
        SpringApplication.run(AicliApplication.class, args);
    }

    @RestController
    public static class AicliController {
        static Map<String, ChatClient> channelsToChatClients = new ConcurrentHashMap<>();
        private final ChatClient.Builder chatClientBuilder;
        private final ChatMemory chatMemory;

        private static final String DEFAULT_SYSTEM_PROMPT = """
                You are a polite and helpful agent. Respond to user's question using what you know. If you don't know the answer simply say 'Sorry, I dont know.'
                """;

        private static final String COMMAND_GENERATOR_SYSTEM_PROMPT = """
                You are a zsh shell expert on Linux. Respond to user's prompt by interpreting as a description of zsh shell command they would like you to generate.
                Only generate a command pipeline if users prompt makes sense as a description of zsh shell command, else just return empty string.
                Do not generate any extra text, just the command.
                Do not wrap in any markup.
                Do not ever, ever reverse quote the generated command.
                """;

        public record AicliRequest(String channel, String prompt, String ZSH_AI_MODEL) {}

        public AicliController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
            this.chatClientBuilder = chatClientBuilder;
            this.chatMemory = chatMemory;
        }

        @PostMapping("/")
        public String aicli(@RequestBody AicliRequest request) {
            ChatClient chatClient = getChatClient(request.channel());
            String content = chatClient.prompt()
                    .user((ChatClient.PromptUserSpec promptUserSpec) -> promptUserSpec.text(request.prompt()))
                    .call()
                    .content();
            if (request.channel().endsWith("-command")) {
                assert content != null;
                content = content.trim().replaceFirst("^`", "").replaceFirst("`$", "");
            }
            return content;
        }

        private ChatClient getChatClient(String channel) {
            return channelsToChatClients.computeIfAbsent(channel, (String channelKey) -> {
                MessageChatMemoryAdvisor messageChatMemoryAdvisor =
                        MessageChatMemoryAdvisor.builder(chatMemory).conversationId(channelKey).build();
                return chatClientBuilder
                        .defaultAdvisors(messageChatMemoryAdvisor)
                        .defaultSystem(channelKey.endsWith("-command") ? COMMAND_GENERATOR_SYSTEM_PROMPT : DEFAULT_SYSTEM_PROMPT)
                        .build();
            });
        }
    }

    @EventListener
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        // The port is available here after the web server has started
        int localPort = event.getWebServer().getPort();
        try {
            java.nio.file.Files.writeString(java.nio.file.Paths.get("/tmp/.aicli.port"), String.valueOf(localPort));
        } catch (IOException ignore) {
        }
    }
}
