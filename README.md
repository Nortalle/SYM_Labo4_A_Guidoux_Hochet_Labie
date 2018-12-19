# Bien commencer le labo de SYM

## Questions à poser :

- Les `Button`sont souvent utilisés uniquement dans la méthode `onCreate()`, est-ce une bonne pratique de les mettre comme attribut de classe ou ils peuvent être _local_ ?

##Faire fonctionner les notifications

Premièrement, vous allez essayer d'envoyer une simple notification sur la natel, là il y aura un problème car vous allez copier-coller le code de la doc proposée dans la donnée

[Create a notification on Wear OS](https://developer.android.com/training/wearables/notifications/creating)

Sauf que depuis Android 8.1, ils ont rajouté un système de channel, pour ajouter + de spécifications dans les notifications que l'on veut ou non. Alors je vous lance sur cette documentation :

[Create and Manage Notification Channels](https://developer.android.com/training/notify-user/channels)

## Notifications sur Wearable

Après, j'ai remarqué que les notifications étaient "cliquable" sur le smartphone, elle ouvrait notre application. Alors que sur le Wearable, non, la notification apparaît, mais n'est pas "cliquable".

### la réponse de Fabien Dutoit : 

Avec  des versions plus anciennes de Wear OS l’intent par défaut  (`setContentIntent`) d’une notification était aussi cliquable sur la  montre, ce n’est plus le cas avec la dernière version. Ils ont remplacé  ceci par l’action inline : [ Add an inline action](https://developer.android.com/training/wearables/notifications/creating#inline) , mais pas besoin de faire cela pour ce laboratoire. Donc pour résumer :

- Notification simple (avec `PendingIntent`)
  - Intent « simple » avec méthode `setContentIntent()`
- Notification avec des Action Buttons (visibles sur le téléphone et la montre)
  - Utilisation de `addAction()` pour plusieurs actions (2-3)
- Notification avec des wearable-only Actions
  - Utilisation de `addAction()` sur un `WearableExtender` pour plusieurs actions