import React from 'react';

const HomePage = React.lazy(() => import('./views/Homepage'));

const routes = [
  { path: '/', exact: true, name: 'Home' },
  { path: '/homepage', name: 'HomePage', component: HomePage },
];

export default routes;
