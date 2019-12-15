# Memória
## Android Profiler
O app foi testado em um aparelho físico (Moto C) e em um emulador (Pixel 2).

O gasto de memória não variou muito entre as atividades Main, Cronometer e Stats. Estas ficaram com um gasto baixo de 20MB para código e 30MB para gráficos.

Porém, na WebView de provas antigas foi visto um aumento considerável para o uso de memória gráfica, com seu valor subindo para 55MB devido ao download de uma página web.

## Leak Canary
Também foi feito um teste com o Leak Canary, que detectou um leak de biblioteca na CronometerActivity.

Após incpecionar, detectei que o problema ocorria devido ao seguinte trecho de código:

```
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(arg0: Animation) {}
                override fun onAnimationRepeat(arg0: Animation) {}
                override fun onAnimationEnd(arg0: Animation) {
                    myAdapter.notifyDataSetChanged() //Refresh list
                }
            })
```
que criava um Listener para a animação de remoção de um adesivo do cronometro mas não o removia posteriorente.

Para resolver este Leak, foi feito uma alteração para remover o Listerner ao completar a ação desejada.

```
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(arg0: Animation) {}
                override fun onAnimationRepeat(arg0: Animation) {}
                override fun onAnimationEnd(arg0: Animation) {
                    myAdapter.notifyDataSetChanged() //Refresh list
                    anim.setAnimationListener(null)
                }
            })
```
Após essa alteração, mais nenhum Leak foi detectado.