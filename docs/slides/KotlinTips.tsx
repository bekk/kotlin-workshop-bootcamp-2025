import React from 'react';
import {
    SlideLayout,
    fadeTransition,
    Heading,
    UnorderedList,
    ListItem,
    Notes,
    Text,
    CodePane,
} from 'spectacle';
import immutabilityCode from './code-examples/immutability.kts?raw';
import imperativeCode from './code-examples/imperativ-vs-functional.kts?raw';
import extensionFunctionsCode from './code-examples/extension-functions.kts?raw';

export const KotlinTips = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading>Kotlin-tips</Heading>
        <UnorderedList>
            <ListItem>Immutability</ListItem>
            <ListItem>Imperativt vs Funksjonelt</ListItem>
            <ListItem>Extension functions</ListItem>
        </UnorderedList>
        <Notes>
            <Text>
                Noen tips for dere som kanskje ikke har brukt kotlin så mye før.
            </Text>
            <Text>
                Går ikke veldig inn i detaljer, men bare for å gi rask insikt i
                noen konsepter. Spør oss gjerne etterpå om noe er uklart og dere
                vil lære mer.
            </Text>
        </Notes>
    </SlideLayout.Full>
);

export const KotlinImmutability = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading>Immutability</Heading>
        <CodePane
            showLineNumbers={true}
            language="kotlin"
            highlightRanges={[
                [1, 3],
                [5, 9],
                [11, 13],
            ]}
        >
            {immutabilityCode}
        </CodePane>
        <Notes>
            <Text>
                Legge til et element i en liste, java-måten bare bruk{' '}
                <code>.add()</code>.
            </Text>
            <Text>
                Men ting er immutable by default i Kotlin, så man må derfor
                gjøre listen muterbar først. Siden <code>.toMutableList()</code>{' '}
                lager en ny liste, må man reassigne.
            </Text>
            <Text>
                "Kotlin-måten" å gjøre det på er å lage en ny liste, og ikke
                muterbere den gamle.
            </Text>
        </Notes>
    </SlideLayout.Full>
);

export const KotlinImperativVsFunksjonelt = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading>Imperativt vs Funksjonel</Heading>
        <CodePane
            showLineNumbers={true}
            language="kotlin"
            highlightRanges={[
                [1, 7],
                [9, 10],
            ]}
        >
            {imperativeCode}
        </CodePane>
        <Notes>
            <Text>
                Første er en typisk implementasjon med en enkel for-løkke.
            </Text>
            <Text>
                For de som har skrevet en del java virker nok dette kjent.
                Veldig mye det samme som <code>stream()</code> APIet der..
            </Text>
            <Text>
                Legg merke til at vi ikke har noen <code>return</code> i den
                nederste. For <em>single expression functions</em> returneres
                returverdien for utrykket.
            </Text>
        </Notes>
    </SlideLayout.Full>
);

export const KotlinExtensionFunctions = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading>Extension functions</Heading>
        <CodePane
            showLineNumbers={true}
            language="kotlin"
            highlightRanges={[
                [1, 2],
                [4, 5],
            ]}
        >
            {extensionFunctionsCode}
        </CodePane>
        <Notes>
            <Text>En måte å uitvide en klasse til å ha flere metoder</Text>
            <Text>
                Vi definerer 2 nye metoder <code>isEven()</code> og{' '}
                <code>isOdd()</code> på <code>Int</code>-klassen.
            </Text>
            <Text>
                Vi kan nå bruke de som alle andre metoder som hører til klassen.
            </Text>
        </Notes>
    </SlideLayout.Full>
);
