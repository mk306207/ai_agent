# 🤖 Conversational support AI Agents
Two collaborative AI agents designed to assist users in problems related to technical support and financial inquiries. The system intelligently routes user queries to the appropriate agent based on the nature of the question, ensuring accurate and context-aware assistance.
# 🛠️ Tech stack
* LLM: GROQ
* Programming Language: Java
* Build Tool: Maven
* IDE: IntelliJ IDEA
* Database: SQLite
* Framework: Langchain4j

# 📋 Requirements
- **Java:** 21 or higher (project uses Java 25)
- **Maven:** 3.6+ (or use IntelliJ's built-in Maven)
- **Groq API Key:** Free tier available at https://console.groq.com
- **IDE:** IntelliJ IDEA (recommended) or any Java IDE

# 🚀 Getting started
1. Clone the repository:
```bash
  git clone https://github.com/mk306207/ai_agent.git
```
2. Set up your environment variables:
   - Create a `.env` file in the root directory.
   - Add your GROQ API key: APIKEY=your_groq_key
3. Add documentation to docs/ folder (doc1.txt, doc2.txt, etc.)
4. Build the project using Maven:
```bash
  mvn clean install
```
5. Run the application: Run the `Main` class located in `src/main/java/Main.java`.
# 💬 Example conversations
**Normal conversation:**
```plaintext
User: "How long does api key last?"
RouterAI: "TECHNICAL"
Agent A (🧑‍💻): "API keys expire after 12 months."
```
```plaintext
User: "What's the weather today?"
RouterAI: ERROR
System: "Sorry, I can only answer technical or financial questions."
```
# 🧑‍💼 Agents overview
## 🧑‍💻 Agent A : Tech support
Agent A is designed to assist users with technical queries, providing explanations, troubleshooting steps, and guidance on various tech-related topics based on documentation you need to provide in folder docs/. This agent will use only knowledge inside those docs. To access the documentations agent a uses RAG.
## 💵 Agent B : Financial support
Agent B focuses on financial topics, helping users resolve problems like refunds, informing them on billing plans and checking their refund status. This agent relies on general financial knowledge and database that stores refund tickets.
## 🤝 Agent collaboration
The system uses a **custom intelligent routing architecture** where three specialized components work together:

1. **Agent A**
2. **Agent B**
3. **RouterAI** - A dedicated LLM-powered classifier that analyzes user queries and determines the appropriate agent


All three components share a **MessageWindowChatMemory** (20 messages), enabling:
- **Context-aware routing** - Router understands conversation context (e.g., during multi-turn refund process)
- **Seamless agent switching** - Users can switch between technical and financial topics in a single conversation
- **Multi-turn conversations** - Each agent sees the full conversation history

When a user poses a question, RouterAI classifies it as TECHNICAL, FINANCIAL, or ERROR. Based on this classification, the system routes the query to the appropriate specialist agent. If the question is outside both domains, the system gracefully informs the user. This custom architecture ensures accurate, context-aware assistance from the right expert agent.

## 🏗️ Architecture

```
                                User Input
                                    v
                            ┌───────────────┐
                            │   RouterAI    │  <- Groq Llama 3.3 70B classifier
                            │  (LLM Agent)  │     Outputs: TECHNICAL | FINANCIAL | ERROR
                            └───────┬───────┘
       ┌──────────┐                 │                ┌──────────┐
       │ Agent B  │ <── FINANCIAL ──├─ TECHNICAL ──> │ Agent A  │  ← RAG with 4 docs
       │(Finance) │                 │                │(Tech)    │     Embeddings + Retrieval
       └─────┬────┘                 │                └──────────┘
             v                      │                
       ┌──────────┐                 │
       │ Database │               ERROR
       └──────────┘                 │
                                    v
          "Sorry, I can only answer technical or financial questions."
```
# ❌ Possible problems
## 📉 Free plan limitations
When running unit tests you may encounter problem with Groq LLM connection. If so, just create new api key and update it in .env file. It is due to free tier api keys limitations.  
Error will look like this:
```plaintext
Exception in thread "main" java.lang.RuntimeException: dev.ai4j.openai4j.OpenAiHttpException: {"error":{"message":"Rate limit reached for model `llama-3.3-70b-versatile` in organization `org_01k8v57vwmfdk8mg1654bs451r` service tier `on_demand` on tokens per day (TPD): Limit 100000, Used 99220, Requested 1012. Please try again in 3m20.448s. Need more tokens? Upgrade to Dev Tier today at https://console.groq.com/settings/billing","type":"tokens","code":"rate_limit_exceeded"}}
```
## 🕵️‍♂️ How to fix it
If you encounter rate limit issues, consider upgrading your Groq plan or wait until the limit resets. You can also optimize your queries to reduce token usage. Or just create new account and link new api key in .env file.

# 🧪 Testing
Unit tests are located in `src/test/java/`, they cover scenarios like:
* Technical questions handled by Agent A
* Financial questions handled by Agent B
* Multi-turn conversations switching between agents
* Error handling for out-of-scope questions
