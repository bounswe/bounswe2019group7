export default {
  items: [
    {
      name: "Home",
      url: "/dashboard",
      icon: "icon-home"
    },
    {
      name: "Events",
      url: "events",
      icon: "icon-calendar",
      badge: {
        variant: "primary",
        text: "NEW"
      },
      attributes: { disabled: false }
    },
    {
      name: "Articles",
      url: "/articles",
      icon: "icon-book-open",
      attributes: { disabled: false }
    },
    {
      name: "My Portfolios",
      url: "portfolios",
      icon: "icon-graph",
      attributes: { disabled: false }
    },
    {
      name: "Trade Indicies",
      url: "",
      icon: "fa fa-money",
      attributes: { disabled: true }
    },
    {
      name: "Stocks",
      url: "",
      icon: "fa fa-pencil-square-o ",
      attributes: { disabled: true }
    },
    {
      name: "Commodities",
      url: "",
      icon: "fa fa-briefcase",
      attributes: { disabled: true }
    },
    {
      name: "Currency",
      url: "currency",
      icon: "icon-chart",
      attributes: { disabled: false }
    },
    {
      name: "Currency Charts",
      url: "currencyCharts",
      icon: "icon-chart",
      attributes: { disabled: false }
    },
    {
      name: "Funds",
      url: "",
      icon: "fa fa-dollar",
      attributes: { disabled: true }
    },
    {
      name: "Search",
      url: "search",
      icon: "fa fa-search",
      attributes: { disabled: false }
    },
    {
      name: "Help",
      url: "",
      icon: "fa fa-info",
      attributes: { disabled: true }
    },
  ]
};
