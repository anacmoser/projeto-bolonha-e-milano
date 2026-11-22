# Bolonha e Milano

Projeto do 4º bimestre da disciplina SPOTDES: um jogo de estratégia econômica
medieval em Java, construído em 8 etapas incrementais.

## O que é o jogo

De 1 a 4 jogadores governam cidades-Estado na Itália medieval, a partir do ano 1400,
no mesmo computador, um turno de cada vez. Cada jogador decide sobre mercado, agricultura,
impostos, construções e defesa. No fim do ano, quem tem prestígio suficiente sobe de título:
Cavaleiro, Barão, Conde, Marquês, Duque e Rei/Rainha.

- **Prestígio** = construções × 10 + soldados × 2 + servos + terras ÷ 100 (florins guardados não contam).
- **Títulos** (prestígio mínimo): Barão 400, Conde 550, Marquês 700, Duque 850, Rei/Rainha 1000. Sobe-se no máximo um título por ano.
- **Fim da partida**: quando alguém vira Rei/Rainha ou ao fim de 1410. Vence o título mais alto (empate: maior prestígio).
- Colher e arrecadar impostos valem uma vez por ano por cidade.

## Como compilar e executar

Requisitos: Java 17, sem ferramenta de build externa. A partir da pasta do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

## Como rodar os testes

```bash
java -cp bin bolonhaemilano.Testes
```

Saída esperada no final: `Aprovados: 37 | Reprovados: 0`.

Testes dos extras: `java -cp bin bolonhaemilano.TestesExtras` (`Aprovados: 16 | Reprovados: 0`).

## Como jogar (menu principal)

```
1 - Novo jogo
2 - Ver status da cidade
3 - Mercado
4 - Agricultura
5 - Governo
6 - Construções
7 - Encerrar turno
8 - Ver classificação
9 - Sobre o jogo
10 - Extras
0 - Sair
```

A opção 7 passa a vez; quando o último jogador encerra o turno, o ano fecha. Exemplo de partida em dupla:
Ana (Bolonha) e Beto (Milano) terminam 1400 com prestígio 410 e 340, e Ana vira Barão.

## Melhorias extras (diferenciais)

Todas ficam no menu **10 - Extras** e não alteram nenhuma regra do enunciado:

- **Salvar e carregar partida** (`Persistencia`): grava a partida em `bolonha-e-milano.sav` (texto simples) e a reconstrói depois, validando o arquivo.
- **Eventos aleatórios** (`Eventos`): seca, ratos, peste, boa safra e invasão no fim de cada ano. Vêm **desligados** por padrão e podem ser ligados no menu Extras. Uma invasão é repelida com 20 ou mais soldados.
- **Conselheiro** (`Conselheiro`): usa a fórmula do prestígio para dizer quantos pontos faltam para o próximo título e quantas construções (e quantos florins) isso exige.
- **Mais testes** (`TestesExtras`): 16 verificações extras para os três itens acima, sem alterar o resultado esperado da classe `Testes` (37 aprovados).

## Estrutura do repositório

| Pasta | Conteúdo |
|---|---|
| `src/bolonhaemilano/` | Código-fonte Java (pacote `bolonhaemilano`), incluindo `Testes.java` |
| `uml/` | Diagramas de classes (um por etapa, cumulativos) e o diagrama de sequência do fim do ano (`.puml` e `.png`) |
| `casos-de-uso/` | Diagramas de casos de uso (um por etapa, cumulativos) (`.puml` e `.png`) |

Diagrama de classes atual: `uml/diagrama-classes-extras.png`.

## Etapas do projeto

1. Estrutura inicial, Main e menu — concluída
2. Classes Jogador e Cidade — concluída
3. Economia: florins, terras e grãos — concluída
4. Agricultura e população — concluída
5. Impostos e governo — concluída
6. Construções e soldados — concluída
7. Rodadas, múltiplos jogadores e vitória — concluída
8. Integração, testes e entrega final — concluída
