<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Valve Data Format IntelliJ Plugin Changelog

## [Unreleased]

### Added

- Add support for platform version `252.*`

### Changed

- `pluginVerifierIdeVersions` – upgrade to `2022.3.3, 2023.1.7, 2023.2.8, 2023.3.8, 2024.1.7, 2024.2.6, 2024.3.6, 2025.1.2, 252.23309.22`

### Fixed

- Fix auto-generated release notes in plugin’s metadata (What’s New section)

## [1.1.4] - 2025-01-24

### Added

- Add support for platform version `251.*`

### Changed

- Replace `org.jetbrains.intellij` with `org.jetbrains.intellij.platform` version `2.0.0`
- `pluginVerifierIdeVersions` – upgrade to `2022.3.3, 2023.1.7, 2023.2.8, 2023.3.8, 2024.1.7, 2024.2.5, 2024.3.2, 251.17181.16`

### Removed

- Remove support for platform version `222.*`

## [1.1.3] - 2024-11-08

### Added

- Add support for platform version `243.*`

### Changed

- `pluginVerifierIdeVersions` – upgrade to `2022.2.5, 2022.3.3, 2023.1.7, 2023.2.8, 2023.3.8, 2024.1.7, 2024.2.4, 2024.3`

## [1.1.2] - 2024-05-19

### Added

- Add support for platform version `242.*`

### Changed

- `pluginVerifierIdeVersions` – upgrade to `2022.2.5, 2022.3.3, 2023.1.6, 2023.2.6, 2023.3.6, 2024.1.1, 2024.2`

## [1.1.1] - 2024-01-20

### Added

- Add support for platform version `241.*`

### Changed

- `pluginVerifierIdeVersions` – upgrade to `2022.2.5, 2022.3.3, 2023.1.5, 2023.2.5, 2023.3.2, 2024.1`

### Fixed

- Find properties recursively for property reference completion

## [1.1.0] - 2023-10-05

### Added

- Add support for platform version `233.*`
- Support for escape sequences: `\t` and `\n`

### Changed

- Refine red valve icons
- `pluginVerifierIdeVersions` – upgrade to `2022.2.5, 2022.3.3, 2023.1.5, 2023.2.2, 2023.3`

### Fixed

- Remove duplicated `%unicode` from JFlex definition

## [1.0.0] - 2023-09-04

### Added

- Basic VDF file syntax
- VDF objects folding
- VDF structure view and structure-aware navigation bar
- VDF formatter and basic code style settings
- VDF commenter
- VDF line marker provider
- VDF find usages provider 
- VDF reference contributor

[Unreleased]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.1.5...main
[1.1.5]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.1.4...v1.1.5
[1.1.4]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.1.3...v1.1.4
[1.1.3]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.1.2...v1.1.3
[1.1.2]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.1.1...v1.1.2
[1.1.1]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.1.0...v1.1.1
[1.1.0]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/releases/tag/v1.0.0
