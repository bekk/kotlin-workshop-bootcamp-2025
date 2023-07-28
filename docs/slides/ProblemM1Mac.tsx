import React from 'react';
import {
    SlideLayout,
    fadeTransition,
    Heading,
    Notes,
    Text,
    Image,
    FlexBox,
} from 'spectacle';
import bildeIntelliJ from './bilder/intellij-mac.png';

export const ProblemM1Mac = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading>Kjent problem på M1 Mac</Heading>
        <FlexBox>
            <Image
                width="600px"
                alt={`Liste over JDKer i intelliJ med Azul Zulu 19 markers`}
                src={bildeIntelliJ}
            />
        </FlexBox>
        <Notes>
            <Text>
                LibGDX fungerer ikke på M1-arkitektur sist vi testet. Må bytte
                til en JDK som emulerer intel.
            </Text>
            <Text>Vi har kun testet med Azul Zulu 19.</Text>
            <Text>Ikke en av de merket med aarch.</Text>
        </Notes>
    </SlideLayout.Full>
);
