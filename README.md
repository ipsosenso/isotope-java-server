Isotope - JAVA server
=====================

Table des matières
==================

* [Installation](#installation)
* [Documentation](#documentation)

## Installation

Pour utiliser isotope sur votre projet (Maven) :

1. Hériter du parent

    ``` xml
    <parent>
        <groupId>com.ipsosenso</groupId>
        <artifactId>isotope-parent</artifactId>
        <version>1.5.0</version>
        <relativePath/>
    </parent>
    ```

2. Ajouter le BOM ipsosenso

    ``` xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.ipsosenso</groupId>
                <artifactId>isotope-bom</artifactId>
                <version>${parent.version}</version>
                <scope>import</scope>
                <type>pom</type>
            </dependency>
        </dependencies>
    </dependencyManagement>
    ```

3. Ajouter la dépendance core (au moins)

    ``` xml
    <dependency>
        <groupId>com.ipsosenso</groupId>
        <artifactId>isotope-core</artifactId>
    </dependency>
    ```
