 import HomeComponent from './component/HomeComponent';
 import LatestComponent from './component/LatestComponent';
 import HistoricalComponent from './component/HistoricalComponent';
 import AccountComponent from './component/AccountComponent';
 import PortfolioComponent from './component/PortfolioComponent';
import React from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import PrivateRoute from './service/PrRoute.js'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/home" element={<HomeComponent />} />
        <Route path="/latest" element={<LatestComponent />} />
        <Route path="/historical" element={<PrivateRoute><HistoricalComponent /></PrivateRoute>} />
        <Route path="/account" element={<PrivateRoute><AccountComponent /></PrivateRoute>} />
        <Route path="/account/portfolio" element={<PrivateRoute><PortfolioComponent /></PrivateRoute>} />
      </Routes>
    </Router>
  )
}

export default App