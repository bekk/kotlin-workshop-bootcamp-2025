import React from 'react';
import {
    SlideLayout,
    Text,
    FlexBox,
    Image,
    fadeTransition,
    Heading,
    Box,
} from 'spectacle';
import vegardPNG from './bilder/vegard.png';
import erikaPNG from './bilder/erika.png';
import sigurdPNG from './bilder/sigurd.png';
import augustPNG from './bilder/august.png';
import frikkPNG from './bilder/frikk.png';
import joakimPNG from './bilder/joakim.png';
import kenanPNG from './bilder/kenan.png';
import ingridPNG from './bilder/ingrid.png';
import tormartinPNG from './bilder/tormartin.png';
import steffenPNG from './bilder/steffen.png';
import styled from '@emotion/styled';

type OmPersonProps = {
    navn: string;
    erfaring: string;
    bildeUrl: string;
};

const OmPerson = ({ navn, bildeUrl, erfaring }: OmPersonProps) => (
    <PersonWrapper>
        <FlexBox flexDirection="column" justifyContent="center">
            <RoundedProfileImage bildeUrl={bildeUrl} />
            <Text
                fontWeight="300"
                textAlign="center"
                padding="0px"
                margin="10px"
                fontSize={20}
            >
                {navn}
            </Text>
            <Text
                fontWeight="300"
                textAlign="center"
                padding="0px"
                margin="0px"
                fontSize={16}
            >
                {erfaring} i Bekk
            </Text>
        </FlexBox>
    </PersonWrapper>
);

export const Hjelpere = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading>Hvem er vi?</Heading>
        <MainWrapper>
            <OmPerson
                navn="Erika Åsberg"
                bildeUrl={erikaPNG}
                erfaring="2 måneder"
            />
            <OmPerson
                navn="Sigurd Hynne"
                bildeUrl={sigurdPNG}
                erfaring="2 måneder"
            />
            <OmPerson navn="August Dahl" bildeUrl={augustPNG} erfaring="1 år" />
            <OmPerson
                navn="Frikk Andersen"
                bildeUrl={frikkPNG}
                erfaring="1 år"
            />
            <OmPerson
                navn="Joakim Sjøhaug"
                bildeUrl={joakimPNG}
                erfaring="1 år"
            />
            <OmPerson navn="Kenan Mahic" bildeUrl={kenanPNG} erfaring="2 år" />
            <OmPerson
                navn="Ingrid Volden"
                bildeUrl={ingridPNG}
                erfaring="2 år"
            />
            <OmPerson
                navn="Vegard Veiset"
                bildeUrl={vegardPNG}
                erfaring="9 år"
            />
            <OmPerson
                navn="Tor Martin Wang"
                bildeUrl={tormartinPNG}
                erfaring="2 måneder"
            />
        </MainWrapper>
    </SlideLayout.Full>
);

export const PresentereOss = () => (
    <SlideLayout.Full transition={fadeTransition}>
        <Heading mb="50px">Kursholdere</Heading>
        <Box mt="60px">
            <MainWrapper>
                <OmPerson
                    navn="Vegard Veiset"
                    bildeUrl={vegardPNG}
                    erfaring="9 år"
                />
                <OmPerson
                    navn="Steffen Hageland"
                    bildeUrl={steffenPNG}
                    erfaring="8 år"
                />
            </MainWrapper>
        </Box>
    </SlideLayout.Full>
);

type RoundedProfileImageProps = {
    bildeUrl: string;
};

const RoundedProfileImage = ({ bildeUrl }: RoundedProfileImageProps) => (
    <ProfileImageWrapper>
        <ProfileImage src={bildeUrl} />
    </ProfileImageWrapper>
);

const ProfileImageWrapper = styled.div`
    width: 180px;
    height: 180px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    border-radius: 50%;
`;

const ProfileImage = styled(Image)`
    max-height: 220px;
    max-width: 220px;
`;

const MainWrapper = styled.div`
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    width: 100%;
`;

const PersonWrapper = styled.div`
    width: 19%;
    margin-top: 10px;
    margin-bottom: 30px;
`;
