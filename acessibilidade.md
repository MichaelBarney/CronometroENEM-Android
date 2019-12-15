# Acessibilidade
Foi realizado um teste manual com o TalkBack no aparelho de teste físico (MotoC).

## Content Description
O primeiro erro de acessibilidade detectado foi nas imagens de informação sobre o dia de prova e de link para o Instagram, pois estas não possuiam descritores do que o botão faz e portanto não tem como ser lido. Para resolver isto, foi adicionado um atributo "ContentDescription" aos elementos:
```
android:contentDescription="Sobre Dia 1"

android:contentDescription="Sobre Dia 2"

android:contentDescription="Abir Instagram"
```

## Accessibility Event
O segundo erro detectado foi na remoção de adesivos da atividade de cronometro. Isto por que, quando um adesivo era removido não havia nenhum aviso pelo TalkBack do que havia ocorrido. Desta maneira, foi colocado um Accessibility Event para focar no Adesivo mais acima no passar do tempo, mantendo a funcionalidade do app mesmo para usuários de TalkBack.
```
val nextView = myRecyclerView?.findViewHolderForAdapterPosition(1)?.itemView
if (nextView != null) {
    nextView.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
};
```
