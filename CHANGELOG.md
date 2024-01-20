<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Valve Data Format IntelliJ Plugin Changelog

## [Unreleased]

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

[Unreleased]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.1.0...main
[1.1.0]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/sczerwinski/valve-data-format-intellij-plugin/releases/tag/v1.0.0
