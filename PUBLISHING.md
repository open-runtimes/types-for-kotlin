# Publishing

This package is published to [Maven Central](https://central.sonatype.com/) as `io.openruntimes:types-for-kotlin`.

## Setup

### 1. Sonatype OSSRH Account

Register at https://central.sonatype.com/ and claim the `io.openruntimes` namespace (requires DNS TXT record verification on `openruntimes.io`).

### 2. GPG Signing Key

Generate a GPG key and upload the public key to a keyserver:

```bash
gpg --full-generate-key
gpg --keyserver keyserver.ubuntu.com --send-keys <KEY_ID>
gpg --export-secret-keys <KEY_ID> > secret-key.gpg
base64 secret-key.gpg > secret-key-base64.txt
```

### 3. GitHub Actions Secrets

Add these secrets to the repository:

| Secret | Description |
|--------|-------------|
| `OSSRH_USERNAME` | Sonatype portal username |
| `OSSRH_PASSWORD` | Sonatype portal password |
| `SONATYPE_STAGING_PROFILE_ID` | Staging profile ID for `io.openruntimes` (find in Sonatype portal under Staging Profiles) |
| `SIGNING_KEY_ID` | Last 8 characters of the GPG key ID |
| `SIGNING_PASSWORD` | GPG key passphrase |
| `SIGNING_SECRET_KEY_RING_FILE` | Absolute path where the key ring will be written (e.g., `/home/runner/secring.gpg`) |
| `GPG_KEY_CONTENTS` | Base64-encoded GPG secret key (contents of `secret-key-base64.txt`) |

## Publishing

Publishing is automated via GitHub Actions. To publish a new version:

1. Create a new GitHub release with a tag matching the desired version (e.g., `0.1.0`)
2. Tags containing `-rc` publish to the snapshot repository
3. All other tags publish to the staging repository and auto-release to Maven Central

## Manual Publishing

```bash
SDK_VERSION=0.1.0 ./gradlew publishToSonatype --max-workers 1 closeAndReleaseSonatypeStagingRepository
```
