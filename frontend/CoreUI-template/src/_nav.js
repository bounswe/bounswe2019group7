export default {
  items: [
    {
      name: 'Home',
      url: '/dashboard',
      icon: 'icon-home',
    }, 
    {
      name: 'Currency',
      url: 'currency',
      icon: 'icon-chart',
      badge: {
        variant: 'primary',
        text: 'NEW'
      },
      attributes: { disabled: false },
    },
    {
      name: 'Events',
      url: 'events',
      icon: 'icon-calendar',
      badge: {
        variant: 'primary',
        text: 'NEW'
      },
      attributes: { disabled: false },
    },
    {
      name: 'Articles',
      url: '',
      icon: 'icon-book-open',
      attributes: { disabled: true },
    },
    {
      name: 'Portfolios',
      url: '',
      icon: 'icon-graph',
      attributes: { disabled: true },
    },
  ]
}
