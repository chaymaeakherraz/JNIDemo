# JNIDemo - Android JNI Lab

## Description

JNIDemo est une application Android développée en Java avec du code natif C++.

Le but de ce laboratoire est de comprendre comment une application Android peut communiquer avec du code C++ à travers JNI.

JNI permet à Java d’appeler des fonctions écrites en C ou C++. Dans ce projet, l’application Android appelle plusieurs fonctions natives, récupère les résultats et les affiche dans l’interface.

---

## Objectif du lab

Ce lab a pour objectif de :

- créer un projet Android avec support C++ ;
- comprendre le rôle de JNI ;
- comprendre le rôle du NDK ;
- comprendre le rôle de CMake ;
- appeler des fonctions natives depuis Java ;
- envoyer des paramètres Java vers C++ ;
- récupérer des résultats calculés côté natif ;
- gérer le chargement de la bibliothèque native `.so` ;
- lire les logs natifs dans Logcat.

---

## Technologies utilisées

- Android Studio
- Java
- C++
- JNI
- Android NDK
- CMake
- Logcat
- Android Emulator

---

## Architecture du projet

Le fonctionnement général de l’application est le suivant :

```text
MainActivity.java
      ↓
Méthodes natives Java
      ↓
System.loadLibrary("native-lib")
      ↓
libnative-lib.so
      ↓
native-lib.cpp
      ↓
Résultats retournés vers Java
      ↓
Affichage dans l’interface Android
```

---

## Structure du projet

```text
JNIDemo/
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/jnidemo/
│   │       │       └── MainActivity.java
│   │       │
│   │       ├── cpp/
│   │       │   ├── native-lib.cpp
│   │       │   └── CMakeLists.txt
│   │       │
│   │       ├── res/
│   │       │   └── layout/
│   │       │       └── activity_main.xml
│   │       │
│   │       └── AndroidManifest.xml
│   │
│   └── build.gradle.kts
│
└── settings.gradle.kts
```

---

## Prérequis

Avant de lancer le projet, il faut installer :

- Android Studio ;
- Android SDK ;
- Android NDK ;
- CMake ;
- LLDB.

Ces composants peuvent être installés depuis :

```text
Tools → SDK Manager → SDK Tools
```

Il faut cocher :

```text
NDK (Side by side)
CMake
LLDB
```

---

## Fonctionnalités réalisées

L’application contient quatre démonstrations JNI.

### 1. Hello from JNI

La méthode Java appelle une fonction C++ qui retourne un texte :

```text
Hello from C++ via JNI !
```

---

### 2. Factoriel natif

L’application envoie un nombre entier depuis Java vers C++.

La fonction C++ calcule le factoriel et retourne le résultat.

Exemple :

```text
factorial(10) = 3628800
```

La fonction gère aussi les erreurs :

```text
factorial(-5) = -1
factorial(20) = -2
```

---

### 3. Inversion d’une chaîne

L’application envoie une chaîne Java vers C++.

Le code C++ inverse la chaîne et retourne le résultat vers Java.

Exemple :

```text
JNI is powerful!
```

devient :

```text
!lufrewop si INJ
```

---

### 4. Somme d’un tableau

L’application envoie un tableau `int[]` vers C++.

Le code natif calcule la somme des éléments.

Exemple :

```text
{10, 20, 30, 40, 50}
```

Résultat :

```text
150
```

---

## Fichiers principaux

### MainActivity.java

Ce fichier contient les méthodes natives déclarées côté Java.

```java
public native String helloFromJNI();
public native int factorial(int n);
public native String reverseString(String s);
public native int sumArray(int[] values);
```

Il charge aussi la bibliothèque native :

```java
static {
    System.loadLibrary("native-lib");
}
```

Le nom `native-lib` doit correspondre au nom défini dans `CMakeLists.txt`.

---

### native-lib.cpp

Ce fichier contient l’implémentation C++ des fonctions natives.

Les fonctions utilisent la syntaxe JNI :

```cpp
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_jnidemo_MainActivity_helloFromJNI(...)
```

Cette signature relie la méthode Java à la fonction C++.

---

### CMakeLists.txt

Ce fichier indique à Android Studio comment compiler le code C++.

Il crée une bibliothèque partagée :

```cmake
add_library(
        native-lib
        SHARED
        native-lib.cpp
)
```

Le résultat est une bibliothèque native `.so`.

---

### activity_main.xml

Ce fichier définit l’interface graphique de l’application.

Il contient plusieurs `TextView` pour afficher :

- le message retourné par C++ ;
- le résultat du factoriel ;
- la chaîne inversée ;
- la somme du tableau ;
- les tests supplémentaires.

---

## Résultat attendu

Après exécution, l’application affiche :

```text
Hello from C++ via JNI !

Factoriel de 10 = 3628800

Texte inverse : !lufrewop si INJ

Somme du tableau = 150

Tests supplémentaires :

factorial(-5) = -1
factorial(20) = -2
reverseString("") =
sumArray(new int[]{}) = 0
```

---

## Vérification avec Logcat

Les logs natifs peuvent être consultés dans Android Studio :

```text
View → Tool Windows → Logcat
```

Chercher le tag :

```text
JNI_DEMO
```

Exemples de logs attendus :

```text
Appel de helloFromJNI depuis le natif
Factoriel de 10 calcule en natif = 3628800
String inversee = !lufrewop si INJ
Somme du tableau = 150
```

---

## Tests réalisés

### Test 1 : Hello JNI

Appel de :

```java
helloFromJNI()
```

Résultat attendu :

```text
Hello from C++ via JNI !
```

---

### Test 2 : Factoriel normal

Appel de :

```java
factorial(10)
```

Résultat attendu :

```text
3628800
```

---

### Test 3 : Factoriel négatif

Appel de :

```java
factorial(-5)
```

Résultat attendu :

```text
-1
```

---

### Test 4 : Factoriel avec dépassement

Appel de :

```java
factorial(20)
```

Résultat attendu :

```text
-2
```

---

### Test 5 : Chaîne inversée

Appel de :

```java
reverseString("JNI is powerful!")
```

Résultat attendu :

```text
!lufrewop si INJ
```

---

### Test 6 : Tableau d’entiers

Appel de :

```java
sumArray(new int[]{10, 20, 30, 40, 50})
```

Résultat attendu :

```text
150
```

---

### Test 7 : Tableau vide

Appel de :

```java
sumArray(new int[]{})
```

Résultat attendu :

```text
0
```

---

## Erreurs fréquentes

### UnsatisfiedLinkError

Cette erreur signifie qu’Android n’arrive pas à trouver la fonction native.

Causes possibles :

- nom de la bibliothèque incorrect ;
- signature JNI incorrecte ;
- package Java différent du package utilisé dans C++ ;
- bibliothèque `.so` non générée ;
- oubli de `System.loadLibrary("native-lib")`.

---

### Erreur NDK

Si Android Studio affiche une erreur liée au NDK, il faut vérifier :

```text
Tools → SDK Manager → SDK Tools
```

Puis installer :

```text
NDK (Side by side)
CMake
LLDB
```

Si une version du NDK est corrompue, il faut installer une version stable comme :

```text
27.2.12479018
```

---

### Erreur CMake

Si CMake ne trouve pas le fichier natif, vérifier que le chemin suivant existe :

```text
app/src/main/cpp/CMakeLists.txt
```

Et vérifier dans `build.gradle.kts` :

```kotlin
externalNativeBuild {
    cmake {
        path = file("src/main/cpp/CMakeLists.txt")
    }
}
```

---

## Bonnes pratiques JNI

- Réduire le nombre d’appels entre Java et C++.
- Garder une API native simple et propre.
- Libérer les ressources JNI après utilisation.
- Utiliser Logcat pour tester et déboguer.
- Vérifier les signatures JNI après changement de package.
- Utiliser C++ pour les traitements utiles : calcul intensif, sécurité, traitement d’image, etc.

---

## Utilité de JNI

JNI est utile dans plusieurs cas :

- calcul intensif ;
- traitement d’image ;
- chiffrement ;
- réutilisation de bibliothèques C/C++ ;
- moteurs audio ou vidéo ;
- logique sensible ;
- optimisation de performance ;
- intégration avec des bibliothèques natives existantes.

---

## Améliorations possibles

Le projet peut être amélioré avec :

- multiplication matricielle native ;
- benchmark Java vs C++ ;
- chiffrement natif ;
- détection d’erreur native ;
- utilisation de `RegisterNatives` ;
- gestion des exceptions Java depuis le code C++ ;
- intégration d’une bibliothèque C++ externe ;
- traitement d’image avec OpenCV.

---

## Auteur

Chaimae Akherraz

---

## Conclusion

Ce laboratoire montre comment une application Android peut communiquer avec du code natif C++ grâce à JNI.

L’application JNIDemo illustre le passage de données entre Java et C++, le chargement d’une bibliothèque native, l’utilisation de CMake, la compilation avec le NDK et l’affichage des résultats dans l’interface Android.

JNI est une solution puissante, mais elle doit être utilisée avec méthode. Elle est surtout utile lorsque le code natif apporte un vrai avantage : performance, réutilisation de code C/C++, traitement complexe ou protection partielle de logique sensible.




https://github.com/user-attachments/assets/55a215de-2552-45a8-8390-b65870f8082e



