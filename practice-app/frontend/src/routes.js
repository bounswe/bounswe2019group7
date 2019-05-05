import React from 'react';

const HomePage = React.lazy(() => import('./views/Homepage'));
const Subscribe = React.lazy(() => import('./views/Subscribe'));
const Events = React.lazy(() => import('./views/Events'));

const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/homepage', name: 'HomePage', component: HomePage },
  { path: '/subscribe', name: 'Subscribe', component: Subscribe },
  { path: '/events', name: 'Events', component: Events },
];

export default routes;
