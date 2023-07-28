import React from 'react';
import {
    SlideLayout,
    Text,
    FlexBox,
    Image,
    fadeTransition,
    Heading,
    Notes,
} from 'spectacle';
import bildeUrl from './bilder/game-animation.gif';

export const SpilletViSkalLage = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading>Hva skal vi lage?</Heading>
        <FlexBox>
            <Image
                width="800px"
                alt={`Bilde av spillet vi skal lage i workshopen`}
                src={bildeUrl}
            />
        </FlexBox>
        <Notes>
            <Text>En lite spill hvor man skal unngå firkanter som faller.</Text>
            <Text>
                Enkel introduksjon til enkle konsepter i spillutvikling. Ting
                som main-loop, input, collision-detection, grafikk.
            </Text>
            <Text>
                Tanken er veldig stegvis og mye håndholding i started. Om man
                blir ferdig kan man bruke kreativiteten og utvide så mye man
                vil.
            </Text>
        </Notes>
    </SlideLayout.Full>
);
