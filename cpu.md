# CPU & Performance	
## Android Profiler
O app foi testado em um aparelho físico (Moto C) e em um emulador (Pixel 2).

### Main Activity
Em ambos os aparelhos, o gasto de CPU foi "Muito Baixo", tendo aproximadamente 60 Threads ativas e utilizando muito pouco do aparelho.

### Cronometer Activity
Esta atividade teve em ambos aparelhos um gasto de 25% da CPU, com 92 threads ativas. Ela é tela com mais funcionalidades e apresentou um desempenho moderado.

### Tests Activity
A atividade mais pesada devido à inicialização e uso da thread do Google Chrome, por ser uma WebView. Chegou a utilizar 33% da CPU dos aparelhos. 

### Stats Activity
Esta atividade foi a mais leve, com um uso extremamente baixo da CPU (20%).
