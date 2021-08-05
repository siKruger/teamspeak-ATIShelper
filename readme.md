# Teamspeak ATIS-Bots

## Instructions
- place bot_identity.ini in main/src/resources/
- modify environment variables
- AVWX for METAR
- VoiceRSS for TTS


## Overview

- user based teamspeak bot
- ATIS/METAR/TAF request via command or speech
- integration with IVAO API to fetch flight information by given callsign/pilot id
- frontend????

## Roadmap
### seperated in different phases

---
**Phase 1/ basic teamspeak functions**

- [x] connect
- [x] disconnect // kinda lol

---
**Phase 2/ basic request functions**
- [ ] METAR/TAF by text
- [ ] response in channel or direct


---
**Phase 3/ advanced response**

- [x] TTS for ATIS

---
**Phase 4/ basic IVAO functions**
- [ ] fetch flight information by ID/callsign/...


---
**Phase 5/ advanced IVAO functions**
- [ ] predict arrival and automated ATIS for arrival


---
**Phase 6/ overkill**
- [ ] speech to text?



####DISCLAIMER
i bet we get to phase 2 before we loose interest :)
