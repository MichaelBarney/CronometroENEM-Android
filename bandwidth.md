# Consumo de Rede
## Android Profiler
Existem apenas 3 cenários onde o app realiza consumo de rede:

### Inicialização dos Anúncios
Consumo muito baixo de dados, sendo apenas uma inicialização da plataforma de anuncios feito assim que o App é aberto. A troca de informação envolve apenas 12KB de dados recebidos somente 5KB de dados enviados.

### Download do Anuncio
Ao abir a atividade do cronometro, é realizado o download do anúncio do tipo banner à ser exibido. Ele chega a atingir uma taxa de 100Kb/s dependendo do tipo de anúncio, sendo ainda considerado um consumo baixo.

### Provas Antigas
Ao abrir a atividade de provas antigas, é feito o download inteiro de uma página web. Esta é com certeza a atividade com maior consumo de rede, atingindo facilmente uma taxa de 1MB/s. 

Uma possível solução para este problema é futuramente fazer o download dos arquivos desta página para torná-las disponíveis de maneira offline.