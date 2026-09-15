import React from 'react';
import ReactDOM from 'react-dom/client';
import { DemoPage } from './pages/Demo';
import { modalStyles } from './components/common/Modal';
import './styles/variables.css';

// 全局样式
const globalStyles = `
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }
  
  body {
    font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
`;

function App() {
  return (
    <>
      <style>{globalStyles}</style>
      <style>{modalStyles}</style>
      <DemoPage />
    </>
  );
}

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
