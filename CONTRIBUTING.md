Guidelines
---

Thank you for contributing to MinigameCore! Have a read through before you start contributing.

### Report issues and requesting features
* Make the title of the report/request clear. A ```Doesn't work` is not very helpful, compared to a `Teleportation Events
don't work as expected`.
* Reporting bugs:
    * Describe how we can reproduce the bug, step by step.
    * Describe what you expected.
    * Describe what actually happened
    * If possible add screenshots and console logs(as [pastebin] or [gists]) where necessary.
* Requesting new features:
    * Tell as what you would like to see.
    * Provide justification/ use cases of how this is useful.
* If the need be, we will ask for more information, so we have it right.

### Pull requests
Like to contribute some code? You are welcome! Just make sure you are aware of some things.

#### Code Style
We tend to follow the [Sponge Code Conventions] but some of
the more important things to note, including changes are:

* Line endings
    * Use Unix line endings when committing (\n).
    * Windows users of git can do `git config --global core.autocrlf true` to let git convert them automatically.

* Column width
    * 150 for Javadocs
    * 120 for code
    * Feel free to wrap when it will help with readability

* Indentation
    * Use 4 spaces for indentations, do not use tabs.

* File headers
    * File headers must contain the license headers for the project as defined in HEADER.txt.
    * You can use `gradle licenseFormat` to automatically to do this for you.

* Imports
    * Imports must be grouped in the following order:
        * static imports
        * *blank line*
        * normal imports
        * *blank line*
        * java imports
        * javax imports
    * Absolutely no wildcard `*` imports.

* Javadocs
    * Do not use @author
    * Wrap additional paragraphs in `<p>` and `</p>`
    * Capitalize the first letter in the descriptions within each “at clause”, i.e. @param name Player to affect, no
    periods
    * Be descriptive when explaining the purpose of the class, interface, enum, method etc.

* Deprecation
    * Do not deprecate content unless you have a replacement, or if the provided feature is removed.
    * Be prepared to provide justification for the deprecation.
    * When deprecating, provide the month and year when it was deprecated.

#### Code Conventions
* Return `java.util.Optional` if you are adding an API method that could return `null`.
* Use `@Nonnull` if `null` is not a valid option.
* Use `@Nullable` if `null` is a valid option.
* Only one declaration per line.
* All uppercase letters for a constant variable. Multiple words should be separated with an underscore - `_`.

#### Submitting your Pull Requests
In your PRs, please make sure you fulfill the following:

* Provide a justification for the change - is it a new feature or a fix to a bug?
* Before sending a pull request ensure that your branch is up to date with the branch you are targeting. This should
normally be master.
* Do not squash commits unless directed to do so, but please _rebase_ your changes on top of master when you feel your
changes are ready to be submitted - _do not merge_. We will squash the commits in a way we feel logical.

[pastebin] : http://www.pastebin.com
[gists] : http://gist.github.com
[Sponge Code Conventions] : https://docs.spongepowered.org/master/en/contributing/implementation/codestyle.html