# Stencil

A flexible and customizable library for Compose Multiplatform that enables synchronized backgrounds across multiple child components.

[![Maven Central](https://img.shields.io/maven-central/v/dev.stetsiuk/compose-stencil.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/dev.stetsiuk/compose-stencil)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Platform Support

| Platform | Status |
|----------|--------|
| Android  | ✅ Supported |
| iOS      | ✅ Supported (arm64, x64, simulatorArm64) |
| Desktop  | ✅ Supported (JVM) |
| Web (JS) | ✅ Supported |
| WebAssembly | ✅ Supported |

## Preview

![Modal Preview](media/preview.gif)

## Features

- **Multiplatform Support**: Works seamlessly across Android, iOS, Desktop, Web (JS), and WebAssembly
- **Synchronized Backgrounds**: Create cohesive visual effects across multiple child components
- **Flexible Positioning**: Customize how backgrounds are positioned and rendered
- **Shape Support**: Apply custom shapes to child components
- **Compose-First**: Built with Jetpack Compose principles for modern UI development

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("dev.stetsiuk:compose-stencil:1.0.0")
}
```

For multiplatform projects, add to `commonMain`:

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("dev.stetsiuk:compose-stencil:1.0.0")
        }
    }
}
```

## Usage

### Basic Example

Create a synchronized gradient background across multiple items:

```kotlin
@Composable
fun StencilExample() {
    val color1 = Color(0xFFE91E63)
    val color2 = Color(0xFF9C27B0)
    val color3 = Color(0xFF3F51B5)

    val shape = RoundedCornerShape(24.dp)
    val state = rememberStencilState()

    Stencil(
        state = state,
        modifier = Modifier.fillMaxSize(),
        backgroundScope = { params ->
            val topLeft = params.relativeToParent
            val size = params.size

            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(color1, color2, color3),
                    start = Offset(0f, topLeft.y),
                    end = Offset(0f, topLeft.y + size.height)
                )
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(20) { index ->
                StencilChild(
                    state = state,
                    shape = shape
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Item $index",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}
```

### Core Components

#### Stencil

The parent component that defines the background and coordinates its child components.

```kotlin
Stencil(
    state: StencilState,
    modifier: Modifier = Modifier,
    backgroundPositionParams: (
        parentParams: PositionInRootParams,
        childParams: PositionInRootParams
    ) -> PositionParams = { parentParams, childParams ->
        PositionParams.default(parentParams, childParams)
    },
    backgroundScope: DrawScope.(PositionParams) -> Unit = {},
    content: @Composable () -> Unit
)
```

#### StencilChild

A child component that receives the synchronized background from its parent Stencil.

```kotlin
StencilChild(
    state: StencilState,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    positionParams: (
        parentParams: PositionInRootParams,
        childParams: PositionInRootParams
    ) -> PositionParams = { parentParams, childParams ->
        PositionParams.default(parentParams, childParams)
    },
    backgroundScope: DrawScope.(PositionParams) -> Unit = { params ->
        // Inherited from parent
    },
    content: @Composable () -> Unit
)
```

#### StencilState

Manages the state and coordinates position information between parent and children.

```kotlin
val state = rememberStencilState(
    initialParentPositionParams: PositionInRootParams = PositionInRootParams()
)
```

## How It Works

Stencil calculates the position of each child component relative to its parent and renders a portion of the shared background. This creates the effect of a continuous background across multiple separated components, perfect for:

- Scrollable lists with gradient backgrounds
- Card-based layouts with unified visual themes
- Dynamic UI elements with synchronized effects
- Creative layouts with complex background patterns

## License

```
Copyright 2024 Vasyl Stetsiuk

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Author

Vasyl Stetsiuk
- GitHub: [@vasyl-stetsiuk](https://github.com/vasyl-stetsiuk)
- Email: stecyuk.vasil@gmail.com