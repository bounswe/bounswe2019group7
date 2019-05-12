import React from 'react';

const HomePage = React.lazy(() => import('./views/Homepage'));
const Subscribe = React.lazy(() => import('./views/Subscribe'));
const Events = React.lazy(() => import('./views/Events'));
const Articles = React.lazy(() => import('./views/Articles'));
const Documentation = React.lazy(() => import('./views/Documentation'));

const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/homepage', name: 'HomePage', component: HomePage },
  { path: '/subscribe', name: 'Subscribe', component: Subscribe },
  { path: '/events', name: 'Events', component: Events },
  { path: '/articles', name: 'Articles', component: Articles },
  { path: '/documentation', name: 'Articles', component: Documentation },

];

export default routes;
