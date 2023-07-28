import React from 'react';
import './fonts/fonts.css';
import { Deck } from 'spectacle';
import { TitleSplash } from './slides/TitleSplash';
import { Hjelpere, PresentereOss } from './slides/PresentereOss';
import { KortOmLibGDX } from './slides/KortOmLibGDX';
import { LinkTilKoden } from './slides/LinkTilKoden';
import { SpilletViSkalLage } from './slides/SpilletViSkalLage';
import { Demo } from './slides/Demo';
import { HvaErKotlin } from './slides/HvaErKotlin';
import { KotlinCodeSlide, KotlinCodeSlideTwoColumns } from './KotlinCodeSlide';

import kotlinFunksjoner from './slides/code-examples/kotlin01-funksjoner.kts?raw';
import namedArguments from './slides/code-examples/kotlin02-named-arguments.kts?raw';
import lambdas from './slides/code-examples/kotlin03-lambdas.kts?raw';
import lister from './slides/code-examples/kotlin04-lists.kts?raw';
import immutability from './slides/code-examples/kotlin05-immutability.kts?raw';
import baseJavaExample from './slides/code-examples/java-base-example.java?raw';
import baseKotlinExample from './slides/code-examples/kotlin-base-example.kts?raw';
import mainLoop from './slides/code-examples/simple-module.kts?raw';

const theme = {
    fonts: {
        header: 'Newzald, Georgia, sans-serif',
        text: "DIN, 'Calibre Light', sans-serif",
    },
    colors: {
        primary: '#FFFFFF',
        secondary: '#FFFFFF',
        tertiary: '#0E0E0E',
    },
};

export const Presentation = () => (
    <Deck theme={theme}>
        <TitleSplash />
        <PresentereOss />
        <Hjelpere />
        <SpilletViSkalLage />
        <Demo />
        <HvaErKotlin />
        <KotlinCodeSlideTwoColumns
            title="Java vs Kotlin"
            codeLeft={baseKotlinExample}
            codeRight={baseJavaExample}
        />
        <KotlinCodeSlide title="Funksjoner" code={kotlinFunksjoner} />,
        <KotlinCodeSlide title="Named arguments" code={namedArguments} />,
        <KotlinCodeSlide title="Lambdas" code={lambdas} />,
        <KotlinCodeSlide title="Lister" code={lister} />,
        <KotlinCodeSlide title="Immutability" code={immutability} />,
        <KortOmLibGDX />
        <KotlinCodeSlide title="Main loop" code={mainLoop} />,
        <LinkTilKoden />
    </Deck>
);
