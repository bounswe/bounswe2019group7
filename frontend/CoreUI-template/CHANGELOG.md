## [CoreUI for Vue.js](./README.md) version `changelog`

##### `v2.1.5`
- fix(Forms): textarea is not textarea #161 - thanks @l2aelba
- chore: lock `bootstrap-vue` to `2.0.0-rc.24`

##### `v2.1.4`
- fix(jest.config): babel-jest can't process import statement
- chore(package.json): add missing `repository` url
- refactor(DefaultContainer): SidebarToggler add defaultOpen prop example
- test: update snapshots
- chore: add ie polyfills
- fix: extract b-card html header to slot 
- fix(Forms): remove deprecated `horizontal` props
- refactor(Dropdowns): add spacings
- chore(vue.config): add publicPath

###### dependencies update
- update `@coreui/coreui` to `^2.1.12`
- update `@coreui/coreui-plugin-chartjs-custom-tooltips` to `^1.3.1`
- update `bootstrap-vue` to `^2.0.0-rc.24`
- update `chart.js` to `^2.8.0`
- update `core-js` to `^2.6.9`
- update `css-vars-ponyfill` to `^2.0.2`
- update `flag-icon-css` to `^3.3.0`
- update `vue` to `^2.6.10`
- update `vue-chartjs` to `^3.4.2`
- update `vue-router` to `^3.0.6"
- update `@vue/cli-plugin-babel` to `^3.8.0`
- update `@vue/cli-plugin-e2e-nightwatch` to `^3.8.0`
- update `@vue/cli-plugin-eslint` to `^3.8.0`
- update `@vue/cli-plugin-unit-jest` to `^3.8.0`
- update `@vue/cli-service` to `^3.8.4`
- update `@vue/test-utils` to `1.0.0-beta.29`
- update `node-sass` to `^4.12.0`
- update `vue-template-compiler` to `^2.6.10`

##### `v2.1.3`
- fix: change bootstrap-vue library to constant version (2.0.0-rc.11) to fix breaking changes from next versions and avoid future breaking changes
- chore: update dependencies
- chore: update test snapshots

##### `v2.1.2`
- chore: update `@coreui/coreui` to `^2.1.6`
- chore: update `@coreui/vue` to `^2.1.2`
- chore: update `core-js` to `^2.6.2`
- chore: update `vue` to `^2.5.22`
- chore: update `vue-template-compiler` to `^2.5.22`

##### `v2.1.1`
- chore: update `@coreui/coreui` to `^2.1.5`
- chore: update `@coreui/vue` to `^2.1.1`
- chore: update `bootstrap` to `^4.2.1`
- chore: update `core-js` to `^2.6.1`
- chore: update `css-vars-ponyfill` to `^1.16.2`
- chore: update `vue` to `^2.5.21`
- chore: update `@vue/cli-plugin-babel` to `^3.3.0`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `^3.3.0`
- chore: update `@vue/cli-plugin-eslint` to `^3.3.0`
- chore: update `@vue/cli-plugin-unit-jest` to `^3.3.0`
- chore: update `@vue/cli-service` to `^3.3.0`
- chore: update `@vue/test-utils` to `^1.0.0-beta.28`
- chore: update `vue-template-compiler` to `^2.5.21`

##### `v2.1.0`
- feat(SidebarNav): navLink `attributes` - optional JS object with valid JS API naming:
  - attributes: `rel`, `target`, `hidden`, `disabled`, etc...
  - starting with `@coreui/coreui ^2.1.4`, `@coreui/vue ^2.1.0`
  - item example(`./src/_nav.js`):
```
{
  name: 'Disabled',
  url: '/disabled',
  icon: 'icon-ban',
  attributes: { disabled: true },
},
{
  name: 'Try CoreUI PRO',
  url: 'https://coreui.io/pro/react/',
  icon: 'cui-layers icons',
  variant: 'danger',
  attributes: { target: '_blank', rel: "noopener" },
},
```
- test: e2e and snapshots update
- chore: update `@coreui/coreui` to `^2.1.4`
- chore: update `@coreui/vue` to `^2.1.0`
- chore: update `core-js` to `^2.6.0`
- chore: update `css-vars-ponyfill` to `^1.15.3`
- chore: update `vue` to `^2.5.19`
- chore: update `vue-router` to `^3.0.2"
- chore: update `vue-template-compiler` to `^2.5.19`
- chore: update `@vue/cli-plugin-babel` to `^3.2.0`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `^3.2.0`
- chore: update `@vue/cli-plugin-eslint` to `^3.2.1`
- chore: update `@vue/cli-plugin-unit-jest` to `^3.2.0`
- chore: update `@vue/cli-service` to `^3.2.0`
- chore: update `@vue/test-utils` to `^1.0.0-beta.27`
- chore: update `node-sass` to `^4.11.0`

##### `v2.0.3`
- test(init): update Tables snapshot
- test(e2e): add `aside-menu-*-show` testing
- chore: update `@coreui/coreui` to `2.1.0`
- chore: update `css-vars-ponyfill` to `1.15.0`
- chore: update `@vue/cli-plugin-babel` to `3.1.1`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `3.1.1`
- chore: update `@vue/cli-plugin-eslint` to `3.1.5`
- chore: update `@vue/cli-plugin-unit-jest` to `3.1.1`
- chore: update `@vue/cli-service` to `3.1.4`
- chore: update `node-sass` to `4.10.0`

##### `v2.0.2`
- refactor: extract random() to `shared/utils`
- refactor: extract shuffleArray() to shared/utils
- refactor: Tables pass items as props to Table
- refactor: some views minor cleanup
- tests(e2e): add some more test cases
- tests(unit): add some more test cases and snapshots
- chore: update `@coreui/coreui` to `2.0.20`
- chore: update `chart.js` to `2.7.3`
- chore: update `@vue/cli-plugin-babel` to `3.0.5`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `3.0.5`
- chore: update `@vue/cli-plugin-eslint` to `3.0.5`
- chore: update `@vue/cli-plugin-unit-jest` to `3.0.5`
- chore: update `@vue/cli-service` to `3.0.5`
- chore: update `node-sass` to `4.9.4`

##### `v2.0.1`
- refactor(Modals): add spacing
- refactor(BrandButtons): add spacing
- chore: update `@coreui/coreui` to `2.0.14`
- chore: update `@coreui/vue` to `2.0.2`
- chore: update `flag-icon-css` to `3.2.0`
- chore: update `@vue/cli-plugin-babel` to `3.0.4`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `3.0.4`
- chore: update `@vue/cli-plugin-eslint` to `3.0.4`
- chore: update `@vue/cli-plugin-unit-jest` to `3.0.4`
- chore: update `@vue/cli-service` to `3.0.4`
- chore: update `@vue/test-utils` to `1.0.0-beta.25`
- chore: update `babel-jest` to `23.6.0`
- chore: update `growl` to `1.10.5`
- chore: update `https-proxy-agent` to `2.2.1`

##### `v2.0.0`
- chore: update `@coreui/vue` to `2.0.0`
- chore: update `@coreui/icons` to `0.3.0`
- refactor(CoreUIIcons): move to `@coreui/icons v0.3.0`
- fix(Dashboard): SocialBoxChartExample height
- fix(Widgets): SocialBoxChartExample height
- fix(Widgets):  Income widgets cols
- test(unit): add test for User.vue
- test: add jest config for coverage

##### `v2.0.0-rc.0`
- test(unit): add some views testing
- test(e2e): add testing for mobile `sidebar-show`
- refactor: card headers margins
- chore: update `vue` to `2.5.17`
- chore: update `vue-template-compiler` to `2.5.17`
- chore: update `@vue/cli-plugin-babel` to `3.0.1`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `3.0.1`
- chore: update `@vue/cli-plugin-eslint` to `3.0.1`
- chore: update `@vue/cli-plugin-unit-jest` to `3.0.1`
- chore: update `@vue/cli-service` to `3.0.1`
- chore: update `@vue/test-utils` to `1.0.0-beta.24`
- chore: update `babel-jest` to `23.4.2`
- chore: update `node-sass` to `4.9.3`
- chore: update `sass-loader` to `7.1.0`
- chore: update `vue-chartjs` to `3.4.0`

##### `v2.0.0-beta.13`
- fix(jest.config.js): solves - _SecurityError: localStorage is not available for opaque origins_
- chore: update `bootstrap` to `4.1.3`
- chore: update `@vue/cli-plugin-babel` to `3.0.0-rc.7`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `3.0.0-rc.7`
- chore: update `@vue/cli-plugin-eslint` to `3.0.0-rc.7`
- chore: update `@vue/cli-plugin-unit-jest` to `3.0.0-rc.7`
- chore: update `@vue/cli-service` to `3.0.0-rc.7`

##### `v2.0.0-beta.7`
- refactor(Footer): move default footer to the template
- fix(Footer): IE sticky footer issue
- chore: update `@coreui/vue` to `2.0.0-rc.3`
- chore: update `bootstrap` to `4.1.2`
- chore: update `@coreui/coreui` to `2.0.4`
- chore: update `@vue/cli-plugin-babel` to `3.0.0-rc.5`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `3.0.0-rc.5`
- chore: update `@vue/cli-plugin-eslint` to `3.0.0-rc.5`
- chore: update `@vue/cli-plugin-unit-jest` to `3.0.0-rc.5`
- chore: update `@vue/cli-service` to `3.0.0-rc.5`
- chore: update `@vue/test-utils` to `1.0.0-beta.21`
- chore: update `babel-jest` to `23.4.0`
- chore: update `node-sass` to `4.9.2`

##### `v2.0.0-beta.6`
- fix: typo `DafaultAside` to `DefaultAside` - thanks @DamianLion
- feat(router): `Users/User Details` Breadcrumb example with `/users/:id`
- refactor(router): add dynamic imports for Webpack code splitting
- refactor: remove empty `<style>` sections from `vue` files
- refactor(Pages): add `b-form`, `b-form-input` and `autocomplete` to Login
- refactor(Pages): add `b-form`, `b-form-input` and `autocomplete` to Register
- chore: update `@coreui/coreui` to `2.0.3`
- chore: update `@vue/cli-plugin-babel` to `3.0.0-rc.3`
- chore: update `@vue/cli-plugin-e2e-nightwatch` to `3.0.0-rc.3`
- chore: update `@vue/cli-plugin-eslint` to `3.0.0-rc.3`
- chore: update `@vue/cli-plugin-unit-jest` to `3.0.0-rc.3`
- chore: update `@vue/cli-service` to `3.0.0-rc.3`
- chore: update `@vue/test-utils` to `^1.0.0-beta.20`
- chore: update `babel-jest` to `23.2.0`

##### `v2.0.0-beta.5`
- chore: move tooling to `vue-cli v3.0.0-rc.2`
- refactor: move from `static/img` to `public/img` dir
- refactor: move `index.thml` to `public` dir
- refactor: move to Jest testing
- tests: fix e2e, unit

##### `v2.0.0-beta.4`
- chore: dependencies update

##### `v2.0.0-beta.3`
- refactor: `getStyles()` back to `@coreui/coreui` version `^2.0.2`
- fix(Dashboard): `width` card-line*-chart-example `ie` issue

##### `v2.0.0-beta.2`
- refactor: `HeaderDropdown` with new slots `header` and `dropdown` *breaking change*
- chore: update `@coreui/vue` to `2.0.0-beta.1`
- chore: dependencies update

##### `v2.0.0-beta.1`
- chore: update `@coreui/vue` to `2.0.0-beta.0`

##### `v2.0.0-beta.0`
- chore: update `core-js` to `2.5.7`

##### `v2.0.0-alpha.1`
- refactor: separation of concerns - (CoreUI template/components) for use CoreUI as npm module
- refactor: project structure change
- refactor: moved to [vuejs-templates](http://vuejs-templates.github.io/webpack/)
- chore: moved to [Semantic Versioning](https://semver.org/)
- refactor: move to [CoreUI-Vue](https://github.com/coreui/coreui-vue) components `v2`
- refactor: rename containers
- refactor(Colors): view layout, sub-components
- refactor(Switches): move to `Switch component v2`
- refactor(Cards): add `transition` to card-header-action `btn-close`
- refactor(Cards): add `collapse` to card-header-action `btn-minimize`
- refactor(Forms): add `transition` to card-header-action `btn-close`
- refactor(Forms): add `autocomplete` attrib
- refactor(Forms): add missing `form` tags
- refactor(Buttons): view rearrange
- refactor(Jumbotrons): view rearrange
- feat(Icons): add `CoreUI Icons` set
- feat(Tabs): add missing feat `tabs`
- feat(Charts): add `CustomTooltips`, fix tooltip `chartId` issue
- refactor: move `scss` to `assets`
- refactor: IE polyfills
- chore: dependencies update
- chore: cleanups

other:

- feat: app-footer fixed
- refactor(dashboard): brand-card, progress-group-bars
- refactor(social-box-chart-example): props
- feat(forms): new `<b-form-input type="date">` example added
- refactor(cards): Card outline, Card accent - title variants added
- feat: card-header-actions
- feat(_nav): Navbars example added
- update: vuejs-templates/webpack to v1.3.1
- fix: eslint `valid template root`
- refactor: card header title spacing
- refactor: btn-brand
- test(e2e): update
- refactor: sidebar
- refactor: chart examples
