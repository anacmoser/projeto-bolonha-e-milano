# Bolonha e Milano

Projeto do 4º bimestre da disciplina SPOTDES: um jogo de estratégia econômica
medieval em Java, construído em 8 etapas incrementais.

## O que é o projeto

Cada jogador governa uma cidade-Estado na Itália medieval (a partir do ano 1400),
decidindo sobre agricultura, impostos, obras e defesa, com o objetivo de conquistar
títulos de nobreza até chegar a rei ou rainha.

## Como compilar e executar

Requisitos: Java 17, sem ferramenta de build externa. A partir da pasta do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

## Estrutura do repositório

| Pasta | Conteúdo |
|---|---|
| `src/bolonhaemilano/` | Código-fonte Java (pacote `bolonhaemilano`) |
| `uml/` | Diagramas de classes UML (cumulativos, `.puml` e `.png`) |
| `casos-de-uso/` | Diagramas de casos de uso (`.puml` e `.png`) |

## Etapas do projeto

1. Estrutura inicial, Main e menu — concluída
2. Classes Jogador e Cidade — concluída
3. Economia: florins, terras e grãos
4. Agricultura e população
5. Impostos e governo
6. Construções e soldados
7. Rodadas, múltiplos jogadores e vitória
8. Integração, testes e entrega final

## Menu atual (Etapa 2)

```
1 - Novo jogo
2 - Ver status da cidade
3 - Sobre o jogo
0 - Sair
```
